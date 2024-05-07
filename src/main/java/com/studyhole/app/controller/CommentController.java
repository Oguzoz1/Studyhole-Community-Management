package com.studyhole.app.controller;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyhole.app.data.CommentPackage;
import com.studyhole.app.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentPackage commentPackage){
        commentService.save(commentPackage);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentPackage>> getAllCommentsforPost(@PathVariable Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<CommentPackage>> getAllCommentsForUser(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsForUser(username));
    }
}
