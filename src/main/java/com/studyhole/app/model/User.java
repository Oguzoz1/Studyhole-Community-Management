package com.studyhole.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
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

    @ElementCollection
    @CollectionTable(name = "user_subscribed_community")
    @Column(name = "community_id")
    private List<Long> subscribedCommunityIds;
}
