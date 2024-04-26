package com.studyhole.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studyhole.app.data.VotePackage;
import com.studyhole.app.model.Comment;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;
import com.studyhole.app.model.Vote.VoteComment;
import com.studyhole.app.model.Vote.VotePost;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "voteId", ignore = true)
    @Mapping(target = "voteType", source = "votePackage.voteType")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    VotePost mapVotePost(VotePackage votePackage, Post post, User user);

    @Mapping(target = "id", source = "votePost.voteId")
    @Mapping(target = "voteType", source = "votePost.voteType")
    VotePackage mapVotePostPackage(VotePost votePost);

    @Mapping(target = "voteId", ignore = true)
    @Mapping(target = "voteType", source = "votePackage.voteType")
    @Mapping(target = "comment", source = "comment")
    @Mapping(target = "user", source = "user")
    VoteComment mapVoteComment(VotePackage votePackage, Comment comment, User user);

    @Mapping(target = "id", source = "voteComment.voteId")
    @Mapping(target = "voteType", source = "voteComment.voteType")
    VotePackage mapVoteCommentPackage(VoteComment voteComment);
}
