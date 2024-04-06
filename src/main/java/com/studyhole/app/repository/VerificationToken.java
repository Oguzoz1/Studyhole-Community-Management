package com.studyhole.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationToken extends JpaRepository<VerificationToken, Long>{
    Optional<VerificationToken> findByToken(String token);
}
