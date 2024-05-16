package com.studyhole.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.Post.PostTemplate;

@Repository
public interface PostTemplateRepository extends JpaRepository<PostTemplate, Long>{
}
