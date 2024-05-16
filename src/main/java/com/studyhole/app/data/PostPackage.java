package com.studyhole.app.data;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostPackage {
    private String communityName;
    private Long postId;
    @NotBlank(message = "Post Title can not be empty or null")
    private String postTitle;
    @NotBlank(message = "Template can not be blank")
    private Long postTemplateId;
    private String url;
}
