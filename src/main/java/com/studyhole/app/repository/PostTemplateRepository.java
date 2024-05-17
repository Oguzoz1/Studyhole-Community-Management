package com.studyhole.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.Community;
import com.studyhole.app.model.Post.PostTemplate;

@Repository
public interface PostTemplateRepository extends JpaRepository<PostTemplate, Long>{
    List<PostTemplate> findAllByOwnerCommunity(Community community);
}
