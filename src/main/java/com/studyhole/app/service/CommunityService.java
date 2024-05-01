package com.studyhole.app.service;

import org.springframework.stereotype.Service;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.mapper.CommunityMapper;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.repository.CommunityRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommunityService  {

    private final CommunityMapper communityMapper;
    private final CommunityRepository communityRepository;
    
    //Services
    private final UserService userService;

    //Do not forget to add transactional to secure consistency (databse related)
    @Transactional
    public CommunityPackage save(CommunityPackage communityPackage){
        User userdetails = userService.getCurrentUser();      
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
        Community com = communityRepository.findById(id)
        .orElseThrow(() -> new RuntimeException(id.toString() + "NOT FOUND"));

        return com;
    }
}
