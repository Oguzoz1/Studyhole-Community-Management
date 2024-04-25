package com.studyhole.app.data;

import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPackage {
    private String username;
    @NotBlank(message = "Comment can not be Empty!")
    private String text;
    //Post names can be same, thus postId must be retrieved.
    private Long postId;
    private Instant createdDate;
}
