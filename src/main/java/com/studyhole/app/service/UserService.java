package com.studyhole.app.service;

import org.springframework.stereotype.Service;

import com.studyhole.app.model.User;
import com.studyhole.app.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User fetchUser(String username){
        User user = userRepository.findByUsername(username).
        orElseThrow(() -> new RuntimeException(username + "does not exist!"));
        return user;
    }
}
