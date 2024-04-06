package com.studyhole.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.Comment;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;

//This is a repository intended to provide methods for interacting with type Comment. 
//This abstracts details of SQL queries and connection management.

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPost(Post post);
    List<Comment> findByUser(User user);
}
