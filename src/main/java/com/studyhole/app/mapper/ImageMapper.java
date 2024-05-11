package com.studyhole.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.studyhole.app.data.ImagePackage;
import com.studyhole.app.model.DataTypes.Image;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    @Mapping(target = "id", source = "image.id")
    @Mapping(target = "imageData", source = "image.imageData")
    ImagePackage mapToPackage(Image image);
}
