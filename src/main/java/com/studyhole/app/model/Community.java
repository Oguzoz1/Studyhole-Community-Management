package com.studyhole.app.model;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.studyhole.app.model.Post.Post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Community {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long communityId;

    @NotBlank(message = "Community Name is Required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;

    //One community can have multiple ownerUser
    @ManyToMany(fetch = LAZY, cascade = CascadeType.ALL)
    private Set<User> ownerUsers;
}