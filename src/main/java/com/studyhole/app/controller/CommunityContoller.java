package com.studyhole.app.controller;

import static org.springframework.http.HttpStatus.OK;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.data.ImagePackage;
import com.studyhole.app.data.UserPackage;
import com.studyhole.app.service.CommunityService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/community")
@AllArgsConstructor
@CrossOrigin
@Slf4j
public class CommunityContoller {

    private final CommunityService communityService;

    @PostMapping
    public ResponseEntity<CommunityPackage> createCommunity(@RequestBody CommunityPackage comPackage){
        return ResponseEntity.status(HttpStatus.CREATED)
         .body(communityService.save(comPackage));
    }
    @PostMapping("/update")
    public ResponseEntity<CommunityPackage> updateCommunity(@RequestBody CommunityPackage comPackage){
        log.info("Update initiated");
        return ResponseEntity.status(HttpStatus.CREATED)
         .body(communityService.update(comPackage));
    }
    @PostMapping("/upload-image/{id}")
    public ResponseEntity<Void> uploadImage(@RequestBody MultipartFile file, @PathVariable Long id) throws IOException{
        communityService.uploadImageToCommunity(id, file);
        log.info("UPLOAD CALLED");
        return new ResponseEntity<>(OK);
    }
    @GetMapping("/image/{id}")
    public ResponseEntity<ImagePackage> getCommunityImage(@PathVariable Long id) throws IOException, DataFormatException{
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getCommunityImage(id));
    }
    @GetMapping
    public ResponseEntity<List<CommunityPackage>> getAllCommunities(){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunities());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CommunityPackage> getCommunity(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getCommunityPackageById(id));
    }
    @GetMapping("/by-name/{communityName}")
    public ResponseEntity<CommunityPackage> getCommunityByName(@PathVariable String communityName){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getCommunityByName(communityName));
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<CommunityPackage> getCommunityByPostId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getCommunityByPostId(id));
    }
    @GetMapping("/owners/{id}")
    public ResponseEntity<List<UserPackage>> getOwnerUsers(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getOwnerUsersByCommunityId(id));
    }
    @GetMapping("/owned-communities/{id}")
    public ResponseEntity<List<CommunityPackage>> getAllCommunitiesByOwnerId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunitiesByOwnerId(id));
    }
    @GetMapping("/member-communities")
    public ResponseEntity<List<CommunityPackage>> getAllCommunitiesByMemberId(){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllCommunitiesByCurrentUserId());
    }
    @GetMapping("/members/{id}")
    public ResponseEntity<List<UserPackage>> getMemberUsers(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(communityService.getAllMembersByCommunityId(id));
    }

}
