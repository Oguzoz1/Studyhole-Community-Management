package com.studyhole.app.model.Token;

import java.time.Instant;

import com.studyhole.app.model.User;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationTokenId;
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private Instant expiryDate;
}
