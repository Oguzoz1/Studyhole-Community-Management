package com.studyhole.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;

@Repository
public interface CommunityRepository extends JpaRepository<Community,Long> {
    //There could only be unique name. Name or not
    Optional<Community> findByName(String communityName);

    Optional<Community> findByCommunityId(Long id);

    //There could be multiple owner own multiple communities.
    List<Community> findByOwnerUsers(User ownerUsers);

    List<Community> findAllByCommunityIdIn(List<Long> ids);
}
