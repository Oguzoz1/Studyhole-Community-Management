package com.studyhole.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyhole.app.data.AuthPackage;
import com.studyhole.app.data.LoginPackage;
import com.studyhole.app.data.RefreshTokenPackage;
import com.studyhole.app.data.RegisterPackage;
import com.studyhole.app.service.AuthService;
import com.studyhole.app.service.RefreshTokenService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    //Post is to manipulate
    //Get is to retrieve data

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegisterPackage registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful",OK);
    }

    @GetMapping("verify/{token}")
    public ResponseEntity<String> verify(@PathVariable String token){
        authService.verifyWithToken(token);
        return new ResponseEntity<>("Account Verification Complete",OK);
    }

    @PostMapping("/login")
    public AuthPackage login(@RequestBody LoginPackage loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthPackage refreshTokens(@Valid @RequestBody RefreshTokenPackage refreshTokenPackage){
        return authService.refreshToken(refreshTokenPackage);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenPackage refreshTokenPackage){
        refreshTokenService.deleteRefreshToken(refreshTokenPackage.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully");
    }
}
