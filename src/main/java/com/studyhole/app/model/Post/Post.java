package com.studyhole.app.model.Post;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Vote.IVoteImpl;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post implements IVoteImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postTitle;
    private String url;
    
    @Lob
    private String descrtiption;

    private Long postTemplateId;
    
    @Builder.Default
    private Integer voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId")
    private Community ownerCommunity;
}
