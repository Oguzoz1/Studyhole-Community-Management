package com.studyhole.app.model.DataTypes;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("IMAGE")
public class ImageField extends DataField{
    
    @Lob
    @Column(name = "picByte", length = 5000)
    private byte[] imageData;
}
