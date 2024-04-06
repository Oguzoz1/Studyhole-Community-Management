package com.studyhole.app.service;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.cfg.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studyhole.app.data.RegisterPackage;
import com.studyhole.app.model.NotificationEmail;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Token.VerificationToken;
import com.studyhole.app.repository.UserRepository;
import com.studyhole.app.repository.VerificationTokenRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    //Service
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    //Repos
    private final UserRepository userRepository;
    private final VerificationTokenRepository vTokenRepository;
    
    public void signup(RegisterPackage registerPackage){
        //User initialization
        User user = new User();
        user.setUsername(registerPackage.getUsername());
        user.setEmail(registerPackage.getEmail());
        user.setPassword(passwordEncoder.encode(registerPackage.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        //Repository extends thus it saves.
        userRepository.save(user);
        
        //Token Generation
        String token = generateVerificationToken(user);
        String websiteDomain = Environment.getProperties().getProperty("website.domain");

        //Requires a class to hold this instead of locally injecting it
        mailService.sendMail(new NotificationEmail("Studyhole Account Activision",
        user.getEmail(), "Hi there :) Thank you for joinin Studyhole to enhance future with us!"
        + "Please proceed to click the following link to finish activating your account:"
        +  websiteDomain + "/api/auth/accountVerification/" + token 
        ));
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        vTokenRepository.save(verificationToken);
        return token;
    }
}
