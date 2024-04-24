package com.studyhole.app.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.Post.Post;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(community.getPosts()))")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    CommunityPackage mapCommunityPackage(Community community);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Community mapDtoToCommunity(CommunityPackage communityPackage);
}
