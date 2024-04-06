package com.studyhole.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Vote.Vote;

public interface VoteRepository extends JpaRepository<Vote,Long>{
    Optional<Vote> findTopByPostAndUserOrderByVoteId(Post post, User currentUser);
    
}
