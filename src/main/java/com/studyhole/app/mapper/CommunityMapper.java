package com.studyhole.app.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studyhole.app.data.CommunityPackage;
import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.Post.Post;

@Mapper(componentModel = "spring")
public interface CommunityMapper {
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(community.getPosts()))")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "publicCommunity", source = "community.publicCommunity")
    @Mapping(target = "members", source = "community.members")
    CommunityPackage mapCommunityPackage(Community community);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "ownerUsers", source = "ownerUsers")
    @Mapping(target = "publicCommunity", source = "communityPackage.publicCommunity")
    @Mapping(target = "members", source = "communityPackage.members")
    Community mapDtoToCommunity(CommunityPackage communityPackage, Set<User> ownerUsers);

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "ownerUsers", source = "ownerUsers")
    @Mapping(target = "publicCommunity", source = "communityPackage.publicCommunity")
    @Mapping(target = "members", source = "communityPackage.members")
    Community mapDtoToCommunity(CommunityPackage communityPackage);
}

