package com.scaler.ekart.services.Security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.ekart.clients.KafkaProducerClient;
import com.scaler.ekart.dtos.EmailDto;
import org.apache.commons.lang3.RandomStringUtils;
import com.scaler.ekart.dtos.security.UserDto;
import com.scaler.ekart.models.Session;
import com.scaler.ekart.models.SessionsStatus;
import com.scaler.ekart.models.Users;
import com.scaler.ekart.repository.SessionRepository;
import com.scaler.ekart.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.*;

@Service
public class AuthService {
    private UserRepository userRepo;
    private SessionRepository sessionRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private KafkaProducerClient kafkaProducerClient;

    @Autowired
    private ObjectMapper objectMapper;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository ,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepository;
        this.sessionRepo = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<Users> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        Users user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong username password");
        }

        String token = RandomStringUtils.randomAlphanumeric(30);

        Map<String, Object>  jsonForJwt = new HashMap<>();
        jsonForJwt.put("email", user.getEmail());
        jsonForJwt.put("created_at", new Date());
        jsonForJwt.put("expiry_at", new Date(LocalDate.now().plusDays(3).toEpochDay()));

        token = Jwts.builder()
                .claims(jsonForJwt)
                .signWith(secretKey)
                .compact();

        Session session = new Session();
        session.setSessionStatus(SessionsStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        session.setCreatedAt(new Date());
        session.setExpiringAt(new Date(LocalDate.now().plusDays(3).toEpochDay()));
        sessionRepo.save(session);

        UserDto userDto = UserDto.from(user);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepo.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return null;
        }

        Session session = sessionOptional.get();

        session.setSessionStatus(SessionsStatus.ENDED);

        sessionRepo.save(session);

        return ResponseEntity.ok().build();
    }

    public UserDto signUp(String email, String password) {
        Users user = new Users();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setCreatedAt(new Date());
        user.setLastUpdatedAt(new Date(LocalDate.now().plusDays(3).toEpochDay()));
        Users savedUser = userRepo.save(user);

        try {
            EmailDto emailDto = new EmailDto();
            emailDto.setTo(email);
            emailDto.setSubject("Welcome to EKart");
            emailDto.setBody("Have a great day");
            emailDto.setFrom("k.singh1105@gmail.com");

            kafkaProducerClient.sendMessage("signup" ,
                    objectMapper.writeValueAsString(emailDto));
        }
        catch (JsonProcessingException exception){
            throw new RuntimeException();
        }
        return UserDto.from(savedUser);
    }

    public SessionsStatus validate(String token, Long userId) {
        Optional<Session> sessionOptional = sessionRepo.findByTokenAndUser_Id(token, userId);

        if (sessionOptional.isEmpty()) {
            return SessionsStatus.ENDED;
        }

        Session session = sessionOptional.get();

        if (!session.getSessionStatus().equals(SessionsStatus.ACTIVE)) {
            return SessionsStatus.ENDED;
        }



        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        String email = (String) claimsJws.getPayload().get("email");
        Optional<Users> userOptional = userRepo.findByEmail(email);
        if (userOptional.isEmpty()) {
            return SessionsStatus.ENDED;
        }

        long tokenExpiry = Long.valueOf(claimsJws.getPayload().get("expiry_at").toString());
        long currentTime = System.currentTimeMillis();

        if (currentTime > tokenExpiry){
            return SessionsStatus.ENDED;
        }

        return SessionsStatus.ACTIVE;
    }

}
