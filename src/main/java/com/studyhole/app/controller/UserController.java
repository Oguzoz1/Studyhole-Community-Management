package com.studyhole.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.service.CommunityService;
import com.studyhole.app.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final CommunityService communityService;

    @PostMapping("/subscribe/{id}")
    public ResponseEntity<Void> subscribe(@PathVariable Long id){
        CommunityPackage comPackage = communityService.getCommunityPackageById(id);
        userService.subscribeUserToCommunity(comPackage);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
