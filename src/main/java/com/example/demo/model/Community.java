package com.example.demo.model;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Community {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    @NotBlank(message = "Community Name is Required")
    private String _name;
    @NotBlank(message = "Description is required")
    private String _description;
    @OneToMany(fetch = LAZY)
    private List<Post> _posts;
    private Instant _createdDate;
    @ManyToOne(fetch = LAZY)
    private User _user;
}