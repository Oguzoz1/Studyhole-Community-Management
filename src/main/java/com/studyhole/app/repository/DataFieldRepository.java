package com.studyhole.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studyhole.app.model.DataTypes.DataField;

@Repository
public interface DataFieldRepository extends JpaRepository<DataField,Long> {
}

