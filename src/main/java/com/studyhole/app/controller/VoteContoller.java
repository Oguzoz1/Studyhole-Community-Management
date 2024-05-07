package com.studyhole.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studyhole.app.data.VotePackage;
import com.studyhole.app.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
@CrossOrigin(origins = "*")
@org.springframework.context.annotation.Lazy
public class VoteContoller {
    private final VoteService voteService;

    @PostMapping("/post")
    public ResponseEntity<Void> votePost(@RequestBody VotePackage votePackage){
        voteService.votePost(votePackage);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comments")
    public ResponseEntity<Void> voteComment(@RequestBody VotePackage votePackage){
        voteService.voteComment(votePackage);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
    