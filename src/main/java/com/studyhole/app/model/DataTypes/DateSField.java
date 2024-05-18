package com.studyhole.app.model.DataTypes;

import java.sql.Date;

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
@DiscriminatorValue("DATE")
@Getter
@Setter
public class DateSField extends DataField{
    @Lob
    private String input;
}
