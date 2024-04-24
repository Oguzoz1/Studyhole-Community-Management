package com.studyhole.app.service;

import java.time.Instant;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.repository.CommunityRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserService userService;
    //Do not forget to add transactional to secure consistency (databse related)
    @Transactional
    public CommunityPackage save(CommunityPackage communityPackage){
        User userdetails = userService.getCurrentUser();
        
        Community newCom =
         Community.builder().name(communityPackage.getCommunityName())
        .description(communityPackage.getDescription())
        .ownerUsers(Collections.singleton(userdetails))
        .createdDate(Instant.now()).build();
        
        var save = communityRepository.save(newCom);
        communityPackage.setId(save.getCommunityId());
        return communityPackage;
    }

    @Transactional
    public List<CommunityPackage> getAllCommunities() {
        return communityRepository.findAll().stream().map(this::mapToData)
        .collect(Collectors.toList());
    }

    private CommunityPackage mapToData(Community community){
        return CommunityPackage.builder().communityName(community.getName())
        .id(community.getCommunityId())
        .numberOfPosts(community.getPosts().size())
        .createdDate(community.getCreatedDate())
        .build();
    }
}
