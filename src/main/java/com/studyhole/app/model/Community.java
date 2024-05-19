package com.studyhole.app.model;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.studyhole.app.model.DataTypes.Image;
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
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private String guidelines;

    @OneToMany(fetch = LAZY)
    private List<Post> posts;
    private Instant createdDate;

    //One community can have multiple ownerUser
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> ownerUsers;

    private boolean publicCommunity;

    @ElementCollection
    @CollectionTable(name = "community_members")
    @Column(name = "user_id")
    private List<Long> memberIds;

    @ElementCollection
    @CollectionTable(name = "community_applied_members")
    @Column(name = "user_id")
    private List<Long> appliedMemberIds;

    @OneToOne(fetch = FetchType.EAGER)
    private Image communityImage;
}