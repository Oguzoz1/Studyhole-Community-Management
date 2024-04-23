package com.studyhole.app.data;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityPackage {
    private Long id;
    private String communityName;
    private String description;
    private Integer numberOfPosts;
    private Instant createdDate;
}
