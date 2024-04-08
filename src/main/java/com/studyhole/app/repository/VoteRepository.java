package com.studyhole.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Vote.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long>{
    Optional<Vote> findTopByPostAndUserOrderByVoteId(Post post, User currentUser);
    
}
