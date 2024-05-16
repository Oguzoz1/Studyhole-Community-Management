package com.studyhole.app.service;

import org.springframework.stereotype.Service;

import com.studyhole.app.model.DataTypes.DataField;
import com.studyhole.app.repository.DataFieldRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DataFieldService {
    private DataFieldRepository dataFieldRepository;

    public void saveDataField(DataField dataField){
        dataFieldRepository.save(dataField);
    }
}
