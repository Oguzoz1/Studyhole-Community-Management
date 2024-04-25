package com.studyhole.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studyhole.app.data.CommentPackage;
import com.studyhole.app.mapper.CommentMapper;
import com.studyhole.app.model.Comment;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.repository.CommentRepository;
import com.studyhole.app.repository.PostRepository;
import com.studyhole.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
public class CommentService {
    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(CommentPackage commentPackage) {
        User user = userService.getCurrentUser();
        Post post = postRepository.findById(commentPackage.getPostId())
        .orElseThrow(() -> new RuntimeException("Post with given ID not found"));
        Comment comment = commentMapper.mapCommentClass(commentPackage, user, post);
        
        commentRepository.save(comment);
    }

    @Transactional
    public List<CommentPackage> getAllCommentsForPost(Long postId){
        Post post = postRepository.findById(postId)
        .orElseThrow(() -> new RuntimeException("Post with given ID not found"));

        var comments = commentRepository.findByPost(post)
        .stream().map(commentMapper::mapCommentPackage).collect(toList());

        return comments;
    }

    @Transactional
    public List<CommentPackage> getAllCommentsForUser(String username){
        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

        var comments = commentRepository.findByUser(user)
        .stream().map(commentMapper::mapCommentPackage)
        .collect(toList());

        return comments;
    }
    
}
