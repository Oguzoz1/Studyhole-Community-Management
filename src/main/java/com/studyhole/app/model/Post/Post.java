package com.studyhole.app.model.Post;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;
    private String postTitle;
    private String url;

    @Lob 
    private String description;
    @Builder.Default
    private Integer voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId")
    private Community ownerCommunity;
}
