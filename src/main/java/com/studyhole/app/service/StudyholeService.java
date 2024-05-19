package com.studyhole.app.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.mapper.CommunityMapper;
import com.studyhole.app.model.Comment;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.repository.CommentRepository;
import com.studyhole.app.repository.CommunityRepository;
import com.studyhole.app.repository.PostRepository;
import com.studyhole.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
/*
 * THIS CLASS ACTS AS METHOD SUPPLIER BETWEEN SERVICE CLASSES TO AVOID CIRCULAR DEPENDENCY.
 * DO NOT USE THIS CLASS FOR CONTROLLERS. CONTROLLERS SHOULD USE SERVICES RELATIVE TO THEIR
 * OWN PURPOSE.
 * 
 * THIS CLASS HELPS TO REDUCE CODE DUPLICATIONS BY NOT REACHING REPOSITORES IN ALL DEPENDENT CLASSES AND
 * PROVIDING COMMONLY USED METHODS FOR SERVICES
 */
@AllArgsConstructor
@Service
public class StudyholeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final CommunityMapper communityMapper;

    //USER 
    @Transactional
    public User getCurrentUser(){
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
        .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    @Transactional
    public User getUserbyUsername(String username){
        User user = (userRepository.findByUsername(username).
        orElseThrow(() -> new UsernameNotFoundException(username + "does not exist!")));
        return user;
    }

    @Transactional
    public User getUserByUserId(Long id) {
        User user = userRepository.findByUserId(id)
        .orElseThrow( ()-> new UsernameNotFoundException(id + "does not exist"));

        return user;
    }
    @Transactional
    public List<User> getAllUsersByIds(List<Long> ids){
        List<User> users = userRepository.findAllByUserIdIn(ids);
        return users;
    }
    //POST
    @Transactional
    public Post getPostById(Long Id){
        Post post = postRepository.findById(Id)
        .orElseThrow(() -> new RuntimeException("Post with given ID not found"));

        return post;
    }

    //Community
    @Transactional
    public Community getCommunityByName(String name){
        Community com = communityRepository.findByName(name)
        .orElseThrow(() -> new RuntimeException(name + " NOT FOUND!!"));

        return com;
    }

    @Transactional
    public List<Community> getAllByCommunityIds(List<Long> ids){
        List<Community> communities = communityRepository.findAllByCommunityIdIn(ids);
        return communities;
    }
    @Transactional
    public Community getCommunityById(Long id){
        Community com = communityRepository.findByCommunityId(id)
        .orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));

        return com;
    }

    //Comment
    @Transactional
    public Comment getCommentById(Long id){
        Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("COMMENT NOT FOUND"));

        return comment;
    }

    
    @Transactional
    public void subscribeUserToCommunity(User user, CommunityPackage comPackage){
        Community com = communityMapper.mapDtoToCommunity(comPackage);
        if (user.getSubscribedCommunityIds().stream().anyMatch(c -> c.equals(com.getCommunityId()))) {
            return;
        }
        com.getMemberIds().add(user.getUserId());
        user.getSubscribedCommunityIds().add(com.getCommunityId());
    
        userRepository.save(user);
        communityRepository.save(com);
    }

}
