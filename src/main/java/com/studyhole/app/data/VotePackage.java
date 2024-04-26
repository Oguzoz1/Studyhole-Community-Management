package com.studyhole.app.data;

import com.studyhole.app.model.Vote.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotePackage {
    private VoteType voteType;
    private Long id;
}
