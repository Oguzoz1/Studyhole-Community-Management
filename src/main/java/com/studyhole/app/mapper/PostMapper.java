package com.studyhole.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.studyhole.app.data.PostPackage;
import com.studyhole.app.data.PostResponsePackage;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Vote.VotePost;
import com.studyhole.app.model.Vote.VoteType;
import com.studyhole.app.repository.CommentRepository;
import com.studyhole.app.repository.VotePostRepository;
import com.studyhole.app.service.UserService;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VotePostRepository voteRepository;
    @Autowired
    private UserService userService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "ownerCommunity", source = "ownerCommunity")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "postTemplateId", source = "postPackage.postTemplateId")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post map(PostPackage postPackage, Community ownerCommunity, User user);

    @Mapping(target = "postId", source = "postId")
    @Mapping(target = "communityName", source = "ownerCommunity.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponsePackage mapToDto(Post post);

    Integer commentCount(Post post){
        return commentRepository.findByPost(post).size();
    }

    Boolean isPostUpVoted(Post post){
        return checkVoteType(post, VoteType.UPVOTE);
    }
    Boolean isPostDownVoted(Post post){
        return checkVoteType(post, VoteType.DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (userService.isLoggedIn()) {
            Optional<VotePost> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                    userService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
