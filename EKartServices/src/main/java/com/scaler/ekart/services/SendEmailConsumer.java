package com.scaler.ekart.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.ekart.dtos.EmailDto;
import com.scaler.ekart.util.SendEmailHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

@Service
public class SendEmailConsumer {
    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "signup", groupId = "emailService")
    public void sendEmail(String message){

        try {
            EmailDto emailDto = objectMapper.readValue(message, EmailDto.class);

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
            props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
            props.put("mail.smtp.port", "465"); //SMTP Port

            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailDto.getFrom(), "");
                }
            };

            Session session = Session.getDefaultInstance(props, auth);
            System.out.println("Session created");
            SendEmailHelper.sendEmail(session, emailDto.getTo(), emailDto.subject, emailDto.getBody());
        }
        catch (JsonProcessingException exception) {
            throw new RuntimeException();
        }

    }
}
