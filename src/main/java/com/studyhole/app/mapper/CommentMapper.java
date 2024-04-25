package com.studyhole.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studyhole.app.data.CommentPackage;
import com.studyhole.app.model.Comment;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "text", source = "commentPackage.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Comment mapCommentClass(CommentPackage commentPackage, User user, Post post);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentPackage mapCommentPackage(Comment comment);
}
