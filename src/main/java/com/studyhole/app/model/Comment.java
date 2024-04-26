package com.studyhole.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Vote.IVoteImpl;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements IVoteImpl{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Builder.Default
    private Integer voteCount = 0;

}