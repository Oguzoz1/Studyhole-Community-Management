package com.studyhole.app.data;

import java.util.List;

import com.studyhole.app.model.Community;
import com.studyhole.app.model.DataTypes.DataField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTemplatePackage {
    private Long id;
    private String templateName;
    private Community ownerCommunity;
    private List<DataField> dataFields;
}
