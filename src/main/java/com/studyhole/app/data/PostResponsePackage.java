package com.studyhole.app.data;

import java.time.Instant;
import java.util.List;

import com.studyhole.app.model.DataTypes.DataField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponsePackage {
    private Long postId;
    private String postTitle;
    private String url;
    private String description;
    private String communityName;
    private Integer voteCount;
    private Integer commentCount;
    private String duration;
    private boolean upVote;
    private boolean downVote;
    private String username;
    private Long postTemplateId;
    private List<DataField> content;
}
