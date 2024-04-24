package com.studyhole.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    private Instant created;
    private boolean enabled;
}
