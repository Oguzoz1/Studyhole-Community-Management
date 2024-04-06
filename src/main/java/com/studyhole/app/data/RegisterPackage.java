package com.studyhole.app.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPackage {
    @Email
    @NotEmpty(message = "EMAIL IS REQUIRED")
    private String email;

    @NotBlank(message = "USERNAME IS REQUIRED")
    private String username;

    @NotBlank(message = "PASSWORD IS REQUIRED")
    private String password;
}
