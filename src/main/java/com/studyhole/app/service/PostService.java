package com.studyhole.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studyhole.app.data.PostPackage;
import com.studyhole.app.data.PostResponsePackage;
import com.studyhole.app.mapper.PostMapper;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.repository.CommunityRepository;
import com.studyhole.app.repository.PostRepository;
import com.studyhole.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
    
    private final CommunityRepository communityRepository;
    private final UserService userService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void save(PostPackage postPackage) {
        Community com = communityRepository.findByName(postPackage.getCommunityName())
        .orElseThrow(() -> new RuntimeException(postPackage.getCommunityName() + " NOT FOUND!!"));
        User currentUser = userService.getCurrentUser();
        Post post =  postMapper.map(postPackage, com, currentUser);
        postRepository.save(post);
    }

    @Transactional
    public PostResponsePackage getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));
        return postMapper.mapToDto(post);
    }
    @Transactional
    public List<PostResponsePackage> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

        var posts = postRepository.findByUser(user).stream().map(postMapper::mapToDto)
        .collect(toList());
        return posts;
    }
    @Transactional
    public List<PostResponsePackage> getPostsByCommunity(Long id) {
        Community com = communityRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));

        var posts = postRepository.findAllByOwnerCommunity(com).stream()
        .map(postMapper::mapToDto).collect(toList());
        return posts;
    }
    @Transactional
    public List<PostResponsePackage> getPosts(String username) {
        var posts = postRepository.findAll().stream().map(postMapper::mapToDto)
        .collect(toList());
        return posts;
    }

}
