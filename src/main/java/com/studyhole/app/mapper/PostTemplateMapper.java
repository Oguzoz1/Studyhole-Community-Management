package com.studyhole.app.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studyhole.app.data.PostTemplatePackage;
import com.studyhole.app.model.Post.PostTemplate;

@Mapper(componentModel = "spring")
public interface PostTemplateMapper {
    @Mapping(target = "id", source = "template.id")
    @Mapping(target = "ownerCommunity", source = "template.ownerCommunity")
    @Mapping(target = "dataFields", source = "template.dataFields")
    @Mapping(target = "templateName", source = "template.templateName")
    PostTemplatePackage mapToPackage(PostTemplate template);

    @InheritInverseConfiguration
    PostTemplate mapToData(PostTemplatePackage tempPackage);

}