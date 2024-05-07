package com.studyhole.app.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.studyhole.app.model.Token.RefreshToken;
import com.studyhole.app.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
@org.springframework.context.annotation.Lazy
public class RefreshTokenService {
    
    private final RefreshTokenRepository refreshTokenRepository;
    
    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }
    @Transactional
    public void validateRefreshToken(String token){
        refreshTokenRepository.findByToken(token)
        .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
