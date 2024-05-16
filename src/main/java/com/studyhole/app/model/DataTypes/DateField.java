package com.studyhole.app.model.DataTypes;

import java.sql.Date;

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
@DiscriminatorValue("DATE")
public class DateField extends DataField{
    @Lob
    private Date input;
}
