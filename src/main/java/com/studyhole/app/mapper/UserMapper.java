package com.studyhole.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studyhole.app.data.UserPackage;
import com.studyhole.app.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "created", source = "user.created")
    @Mapping(target = "enabled", source = "user.enabled")
    @Mapping(target = "subscribedCommunityIds", source = "user.subscribedCommunityIds")
    UserPackage mapToPackage(User user);
}
