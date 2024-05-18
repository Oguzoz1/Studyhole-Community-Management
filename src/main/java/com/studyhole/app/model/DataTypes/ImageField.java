package com.studyhole.app.model.DataTypes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("IMAGE")
@Getter
@Setter
public class ImageField extends DataField{
    //IMAGEID STORED HERE  
    private Long input;
}
