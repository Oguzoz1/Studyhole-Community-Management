package com.studyhole.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studyhole.app.data.PostPackage;
import com.studyhole.app.data.PostResponsePackage;
import com.studyhole.app.mapper.PostMapper;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.repository.PostRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    
    //Services
    private final UserService userService;
    private final CommunityService communityService;

    public void save(PostPackage postPackage) {
        Community com = communityService.getCommunityByName(postPackage.getCommunityName());
        User currentUser = userService.getCurrentUser();
        Post post =  postMapper.map(postPackage, com, currentUser);
        postRepository.save(post);
    }

    @Transactional
    public PostResponsePackage getPostResponsePackagebyId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));
        return postMapper.mapToDto(post);
    }
    @Transactional
    public List<PostResponsePackage> getPostsByCommunity(Long id) {
        Community com = communityService.getCommunityById(id);
        
        var posts = postRepository.findAllByOwnerCommunity(com).stream()
        .map(postMapper::mapToDto).collect(toList());
        return posts;
    }

    @Transactional
    public List<PostResponsePackage> getPostResponsesPakcagesByUsername(String username) {
        User user = userService.getUserbyUsername(username);
        var posts = postRepository.findByUser(user).stream().map(postMapper::mapToDto)
        .collect(toList());
        return posts;
    }
    @Transactional
    public List<PostResponsePackage> getAllPosts(String username) {
        var posts = postRepository.findAll().stream().map(postMapper::mapToDto)
        .collect(toList());
        return posts;
    }

    //Intended for method-use
    @Transactional
    public Post getPostById(Long Id){
        Post post = postRepository.findById(Id)
        .orElseThrow(() -> new RuntimeException("Post with given ID not found"));

        return post;
    }
}
