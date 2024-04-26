package com.studyhole.app.model.Vote;

import lombok.*;

import com.studyhole.app.model.Comment;
import com.studyhole.app.model.User;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteComment implements IVote{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long voteId;
    private VoteType voteType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId", referencedColumnName = "commentId")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;
}
