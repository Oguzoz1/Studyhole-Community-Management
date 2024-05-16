package com.studyhole.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.studyhole.app.data.PostPackage;
import com.studyhole.app.data.PostResponsePackage;
import com.studyhole.app.data.PostTemplatePackage;
import com.studyhole.app.mapper.PostMapper;
import com.studyhole.app.mapper.PostTemplateMapper;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.repository.PostRepository;
import com.studyhole.app.repository.PostTemplateRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final PostTemplateRepository templateRepository;
    private final PostTemplateMapper templateMapper;
    //Services
    private final StudyholeService studyholeService;

    public PostResponsePackage save(PostPackage postPackage) {
        Community com = studyholeService.getCommunityByName(postPackage.getCommunityName());
        User currentUser = studyholeService.getCurrentUser();
        Post post =  postMapper.map(postPackage, com, currentUser);
        var savePost = postRepository.save(post);

        return postMapper.mapToDto(savePost);
    }
    @Transactional
    public PostTemplatePackage saveTemplate(PostTemplatePackage templatePackage,Long id) {
        Community com = studyholeService.getCommunityById(id);
        templatePackage.setOwnerCommunity(com);
        var save = templateRepository.save(templateMapper.mapToData(templatePackage));
    
        return templateMapper.mapToPackage(save);
    }
    @Transactional
    public PostResponsePackage getPostResponsePackagebyId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));
        return postMapper.mapToDto(post);
    }
    @Transactional
    public List<PostResponsePackage> getPostsByCommunity(Long id) {
        Community com = studyholeService.getCommunityById(id);
        
        var posts = postRepository.findAllByOwnerCommunity(com).stream()
        .map(postMapper::mapToDto).collect(toList());
        return posts;
    }

    @Transactional
    public List<PostResponsePackage> getPostResponsesPakcagesByUsername(String username) {
        User user = studyholeService.getUserbyUsername(username);
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
