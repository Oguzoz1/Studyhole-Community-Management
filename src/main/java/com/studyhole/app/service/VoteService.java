package com.studyhole.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.studyhole.app.data.VotePackage;
import com.studyhole.app.mapper.VoteMapper;
import com.studyhole.app.model.Comment;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Vote.IVoteImpl;
import com.studyhole.app.model.Vote.VoteComment;
import com.studyhole.app.model.Vote.VotePost;
import com.studyhole.app.model.Vote.VoteType;
import com.studyhole.app.repository.CommentRepository;
import com.studyhole.app.repository.PostRepository;
import com.studyhole.app.repository.VoteCommentRepository;
import com.studyhole.app.repository.VotePostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {
    
    private final VoteMapper voteMapper;
    private final VotePostRepository votePostRepository;
    private final VoteCommentRepository voteCommentRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    //Services
    private final StudyholeService studyholeService;

    public void votePost(VotePackage votePackage) {
        Post post = studyholeService.getPostById(votePackage.getId());
        Optional<VotePost> voteByPostandUser = votePostRepository.findTopByPostAndUserOrderByVoteIdDesc
        (post, studyholeService.getCurrentUser());

        //If vote exists and if existing vote is equal to the "requested" vote
        if(voteByPostandUser.isPresent()
        && voteByPostandUser.get().getVoteType().equals(votePackage.getVoteType())){
            throw new RuntimeException("You have already voted");
        }

        vote(post,votePackage);

        //Save
        VotePost vp = 
        voteMapper.mapVotePost(votePackage, post, studyholeService.getCurrentUser());
        
        votePostRepository.save(vp);
        postRepository.save(post);
    }

    public void voteComment(VotePackage votePackage) {
        Comment comment = studyholeService.getCommentById(votePackage.getId());
        Optional<VoteComment> voteByPostandUser = voteCommentRepository.findTopByCommentAndUserOrderByVoteIdDesc
        (comment, studyholeService.getCurrentUser());

        //If vote exists and if existing vote is equal to the "requested" vote
        if(voteByPostandUser.isPresent()
        && voteByPostandUser.get().getVoteType().equals(votePackage.getVoteType())){
            throw new RuntimeException("You have already voted");
        }

        vote(comment,votePackage);

        //Save
        VoteComment vc = 
        voteMapper.mapVoteComment(votePackage, comment, studyholeService.getCurrentUser());
        
        voteCommentRepository.save(vc);
        commentRepository.save(comment);
    }

    private void vote(IVoteImpl votImp, VotePackage votePackage){

        //Check VoteType and act according to that
        if (VoteType.UPVOTE.equals(votePackage.getVoteType())){
            votImp.setVoteCount(votImp.getVoteCount() + 1);
        }
        else{
            votImp.setVoteCount(votImp.getVoteCount() - 1);
        }

    }
}
