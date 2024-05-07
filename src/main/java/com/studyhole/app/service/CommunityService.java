package com.studyhole.app.service;

import org.springframework.stereotype.Service;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.data.UserPackage;
import com.studyhole.app.mapper.CommunityMapper;
import com.studyhole.app.mapper.UserMapper;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.repository.CommunityRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommunityService  {

    private final CommunityMapper communityMapper;
    private final UserMapper userMapper;
    private final CommunityRepository communityRepository;

    //Services
    private final StudyholeService studyholeService;
    
    //Do not forget to add transactional to secure consistency (databse related)
    @Transactional
    public CommunityPackage save(CommunityPackage communityPackage){
        User userdetails = studyholeService.getCurrentUser();      
        var save = communityRepository.save(communityMapper.mapDtoToCommunity(communityPackage,Collections.singleton(userdetails)));
        communityPackage.setCommunityId(save.getCommunityId());

        //First time saving a new community.
        if (save.getOwnerUsers() == null){
            communityPackage.setOwnerUsers(Collections.singleton(userdetails));
        }
        return communityPackage;
    }


    @Transactional
    public List<CommunityPackage> getAllCommunities() {
        return communityRepository.findAll().stream().map(communityMapper:: mapCommunityPackage)
        .collect(Collectors.toList());
    }

    @Transactional
    public CommunityPackage getCommunityPackageById(Long id) {
        Community com = communityRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ID NOT FOUND"));
        return communityMapper.mapCommunityPackage(com);
    }

    @Transactional
    public CommunityPackage getCommunityPackagebyName(String name){
        Community com = communityRepository.findByName(name)
        .orElseThrow(() -> new RuntimeException(name + " NOT FOUND!!"));

        return communityMapper.mapCommunityPackage(com);
    }

    //Intended for method-use
    @Transactional
    public Community getCommunityByName(String name){
        Community com = communityRepository.findByName(name)
        .orElseThrow(() -> new RuntimeException(name + " NOT FOUND!!"));

        return com;
    }

    //Intended for method-use
    @Transactional
    public Community getCommunityById(Long id){
        Community com = communityRepository.findByCommunityId(id)
        .orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));

        return com;
    }

    @Transactional
    public List<UserPackage> getOwnerUsersByCommunityId(Long id){
        Community com = getCommunityById(id);

        List<UserPackage> users = new ArrayList<>(com.getOwnerUsers()).stream().map(userMapper::mapToPackage)
        .collect(toList());
        return users;
    }

    @Transactional
    public List<CommunityPackage> getAllCommunitiesByOwnerId(Long id) {
        User user = studyholeService.getUserByUserId(id);

        return communityRepository.findByOwnerUsers(user).stream().map(communityMapper::mapCommunityPackage)
        .collect(Collectors.toList());
    }

    @Transactional
    public CommunityPackage getCommunityByPostId(Long id){
        Post post = studyholeService.getPostById(id);

        return communityMapper.mapCommunityPackage(post.getOwnerCommunity());
    }
    @Transactional
    public List<UserPackage> getAllMembersByCommunityId(Long id){
        Community com = getCommunityById(id);

        List<UserPackage> members =
        new ArrayList<>(com.getMembers()).stream().map(userMapper::mapToPackage).collect(toList());
        return members;
    }
}
