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

    private final CommunityRepository communityRepository;
    private final UserService userService;
    private final CommunityMapper communityMapper;

    //Do not forget to add transactional to secure consistency (databse related)
    @Transactional
    public CommunityPackage save(CommunityPackage communityPackage){
        User userdetails = userService.getCurrentUser();      
        var save = communityRepository.save(communityMapper.mapDtoToCommunity(communityPackage));
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

    public CommunityPackage getCommunity(Long id) {
        Community com = communityRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("ID NOT FOUND"));
        return communityMapper.mapCommunityPackage(com);
    }
}
