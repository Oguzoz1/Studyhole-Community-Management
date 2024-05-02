package com.studyhole.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.data.UserPackage;
import com.studyhole.app.service.CommunityService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CommunityContoller {

    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<CommunityPackage> createCommunity(@RequestBody CommunityPackage comPackage){
        return ResponseEntity.status(HttpStatus.CREATED)
         .body(communityService.save(comPackage));
    }

    @GetMapping
    public ResponseEntity<List<CommunityPackage>> getAllCommunities(){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityPackage> getCommunity(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getCommunityPackageById(id));
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<List<UserPackage>> getOwnerUsers(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getOwnerUsersByCommunityId(id));
    }

    @GetMapping("/owned-communities/{id}")
    public ResponseEntity<List<CommunityPackage>> getAllCommunitiesByOwnerId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunitiesByOwnerId(id));
    }
}
