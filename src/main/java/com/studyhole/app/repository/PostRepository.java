package com.studyhole.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllByCommunity(Community community);
    List<User> findByUser(User user);
}
