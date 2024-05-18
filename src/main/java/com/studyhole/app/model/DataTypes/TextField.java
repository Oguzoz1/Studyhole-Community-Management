package com.studyhole.app.model.DataTypes;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("TEXT")
@Getter
public class TextField extends DataField {

    @Lob 
    private String input;
}
