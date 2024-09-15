package com.scaler.ekart.services.Security;

import com.scaler.ekart.dtos.security.UserDto;
import com.scaler.ekart.models.Roles;
import com.scaler.ekart.models.Users;
import com.scaler.ekart.repository.RoleRepository;
import com.scaler.ekart.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails(Long userId) {
        Optional<Users> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null;
        }

        return UserDto.from(userOptional.get());
    }

    public UserDto setUserRoles(Long userId, List<Long> roleIds) {
        Optional<Users> userOptional = userRepository.findById(userId);
        List<Roles> roles = roleRepository.findAllByIdIn(roleIds);

        if (userOptional.isEmpty()) {
            return null;
        }

        Users user = userOptional.get();

        Users savedUser = userRepository.save(user);

        return UserDto.from(savedUser);
    }
}