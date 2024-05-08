package com.studyhole.app.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.data.UserPackage;
import com.studyhole.app.mapper.CommunityMapper;
import com.studyhole.app.mapper.UserMapper;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.repository.CommunityRepository;
import com.studyhole.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;
    private final StudyholeService studyholeService;
    private final UserMapper userMapper;

    @Transactional
    public Optional<User> fetchUserOptional(String username){
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username).
        orElseThrow(() -> new UsernameNotFoundException(username + "does not exist!")));
        return user;
    }

    @Transactional
    public UserPackage getUserbyUsername(String username){
        User user = (userRepository.findByUsername(username).
        orElseThrow(() -> new UsernameNotFoundException(username + "does not exist!")));

        return userMapper.mapToPackage(user);
    }

    @Transactional
    public List<UserPackage> getAllByIdList(List<Long> ids){
        List<User> users = userRepository.findAllByUserIdIn(ids);
        return users.stream().map(user -> userMapper.mapToPackage(user))
        .collect(toList());
    }

    @Transactional
    public User getCurrentUser(){
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
        .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
    @Transactional
    public User getUserByUserId(Long id) {
        User user = userRepository.findByUserId(id)
        .orElseThrow( ()-> new UsernameNotFoundException(id + "does not exist"));

        return user;
    }

    @Transactional
    public List<CommunityPackage> getSubscribedCommunitiesbyUserId(Long id){
        User user = getUserByUserId(id);
        List<Community> communities = studyholeService.getAllByCommunityIds(user.getSubscribedCommunityIds());
        List<CommunityPackage> coms = communities.stream().map(com -> communityMapper.mapCommunityPackage(com))
        .collect(toList());
        return coms;
    }

    @Transactional
    public void subscribeUserToCommunity(CommunityPackage comPackage){
        User user = getCurrentUser();
        Community com = communityMapper.mapDtoToCommunity(comPackage);
        if (user.getSubscribedCommunityIds().stream().anyMatch(c -> c.equals(com.getCommunityId()))) {
            log.error("ALREADY EXISTS");
            return;
        }
        com.getMemberIds().add(user.getUserId());
        user.getSubscribedCommunityIds().add(com.getCommunityId());
    
        userRepository.save(user);
        communityRepository.save(com);
    }

    @Transactional
    public void LeaveFromCommunity(CommunityPackage comPackage){
        User user = getCurrentUser();
        Community com = communityMapper.mapDtoToCommunity(comPackage);

        if(com.getMemberIds().contains(user.getUserId())){
            com.getMemberIds().remove(user.getUserId());
            user.getSubscribedCommunityIds().remove(com.getCommunityId());
        }

        userRepository.save(user);
        communityRepository.save(com);
    }
}
