package com.studyhole.app.model.DataTypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "dataFieldType", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TextField.class, name = "TextField"),
    @JsonSubTypes.Type(value = ImageField.class, name = "ImageField"),
    @JsonSubTypes.Type(value = UrlField.class, name = "UrlField"),
    @JsonSubTypes.Type(value = DateSField.class, name = "DateSField"),

})
public class DataField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected Long postId;
    
    @NotBlank(message = "Cannot be blank!")
    protected String name;
}
