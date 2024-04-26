package com.studyhole.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.Comment;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Vote.VoteComment;

@Repository
public interface VoteCommentRepository extends JpaRepository<VoteComment,Long>{
    Optional<VoteComment> findTopByCommentAndUserOrderByVoteIdDesc(Comment comment, User currentUser);
}
