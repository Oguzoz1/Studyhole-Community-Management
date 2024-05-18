package com.studyhole.app.model.DataTypes;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@DiscriminatorValue("URL")
@Getter
@Setter
public class UrlField extends DataField{
    
    @Lob
    private String input;
}
