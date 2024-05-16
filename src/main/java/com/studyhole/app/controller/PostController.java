package com.studyhole.app.controller;

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

import com.studyhole.app.data.PostPackage;
import com.studyhole.app.data.PostResponsePackage;
import com.studyhole.app.data.PostTemplatePackage;
import com.studyhole.app.service.PostService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
@CrossOrigin
@Slf4j
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostResponsePackage> createPost(@RequestBody PostPackage postPackage){
        PostResponsePackage s = postService.save(postPackage);
        return ResponseEntity.status(HttpStatus.CREATED).body(s);
    }

    @PostMapping("/template/{id}")
    public ResponseEntity<PostTemplatePackage> createPostTemplate(@RequestBody PostTemplatePackage templatePackage,
     @PathVariable Long id){
        log.info("Template Creation Initiated");
        PostTemplatePackage t = postService.saveTemplate(templatePackage, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }
    
    @GetMapping("/{id}")
    public PostResponsePackage getPost(@PathVariable Long id){
        return postService.getPostResponsePackagebyId(id);
    }
    @GetMapping("/")
    public List<PostResponsePackage> getPosts(String username){
        return postService.getAllPosts(username);
    }
    @GetMapping("/community/{id}")
    public List<PostResponsePackage> getPostsByCommunity(@PathVariable Long id){
        return postService.getPostsByCommunity(id);
    }
    @GetMapping("/user/{username}")
    public List<PostResponsePackage> getPostsByUsername(@PathVariable String username){
        return postService.getPostResponsesPakcagesByUsername(username);
    }
}
