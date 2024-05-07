package com.studyhole.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
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
    @ManyToMany
    @JsonBackReference
    @JoinTable(
        name = "user_subscriptions",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    private List<Community> subscribedCommunities;
}
