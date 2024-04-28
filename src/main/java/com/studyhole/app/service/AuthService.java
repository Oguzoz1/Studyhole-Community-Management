package com.studyhole.app.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.cfg.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.studyhole.app.authentication.JwtProvider;
import com.studyhole.app.config.AppConfig;
import com.studyhole.app.data.AuthPackage;
import com.studyhole.app.data.LoginPackage;
import com.studyhole.app.data.RefreshTokenPackage;
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
    private final UserService userService; 
    private final RefreshTokenService refreshTokenService;

    //Repos
    private final UserRepository userRepository;
    private final VerificationTokenRepository vTokenRepository;

    //Auth Man
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final AppConfig appConfig;

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

        NotificationEmail email = new NotificationEmail(user.getEmail(),"Studyhole Account Activision", "Hi there :) Thank you for joinin Studyhole to enhance future with us!"
        + "Please proceed to click the following link to finish activating your account:"
        +  appConfig.getUrl() + "/api/auth/verify/" + token);

        mailService.sendMail(email);
    }
    //Generates token for account verification
    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        vTokenRepository.save(verificationToken);
        return token;
    }
    //Account Verification service
    public void verifyWithToken(String token) {
        Optional<VerificationToken> verificationToken = vTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new RuntimeException("Invalid or Missing Token"));
        enableUser(verificationToken.get());
    }

    @Transactional
    private void enableUser(VerificationToken token){
        //Get user from token
        User user = userService.fetchUserOptional(token.getUser().getUsername()).get();
        //Enable user
        user.setEnabled(true);
        //Save user again to the db.
        userRepository.save(user);
    }

    public AuthPackage login(LoginPackage loginRequest) {
        //Generate User-Based token
        UsernamePasswordAuthenticationToken token 
        = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        //Get Authentication with the token
        var auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        //Generate user-based token for login
        String authTok = jwtProvider.generateUserToken(auth);

        AuthPackage authPackage = AuthPackage.builder().authToken(authTok)
        .refreshToken(refreshTokenService.generateRefreshToken().getToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
        .username(loginRequest.getUsername()).build();

        return authPackage;
    }

    public AuthPackage refreshToken(RefreshTokenPackage refreshTokenPackage){
        refreshTokenService.validateRefreshToken(refreshTokenPackage.getRefreshToken());
        String token = jwtProvider.generateUserTokenWithUserName(refreshTokenPackage.getUsername());

        AuthPackage authPackage =
        AuthPackage.builder().authToken(token)
        .refreshToken(refreshTokenPackage.getRefreshToken())
        .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
        .username(refreshTokenPackage.getUsername())
        .build();

        return authPackage;
    }

}
