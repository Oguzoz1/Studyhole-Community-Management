package com.studyhole.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Vote.VotePost;

@Repository
public interface VotePostRepository extends JpaRepository<VotePost,Long>{
    Optional<VotePost> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
