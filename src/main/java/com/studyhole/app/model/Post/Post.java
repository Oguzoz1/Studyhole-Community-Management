package com.studyhole.app.model.Post;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.studyhole.app.model.Community;
import com.studyhole.app.model.User;
import com.studyhole.app.model.DataTypes.DataField;
import com.studyhole.app.model.Vote.IVoteImpl;


@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post implements IVoteImpl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String postTitle;
    private String url;
    
    @Lob
    private String description;

    private Long postTemplateId;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<DataField> content = new ArrayList<>();

    @Builder.Default
    private Integer voteCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "communityId", referencedColumnName = "communityId")
    private Community ownerCommunity;
}
