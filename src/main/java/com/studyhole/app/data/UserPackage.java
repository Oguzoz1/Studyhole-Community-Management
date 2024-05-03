package com.studyhole.app.data;

import java.time.Instant;
import java.util.List;

import com.studyhole.app.model.Community;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPackage {
    private Long userId;
    private String username;
    // private String password;
    // private String email;
    private Instant created;
    private boolean enabled;
    private List<Community> subscribedCommunities;
}
