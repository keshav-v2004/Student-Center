package com.studentManagement.Student.Center.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank
    private String username;

    @NotBlank
    private String email;

    @NotBlank
    private String password; // stored as hashed (BCrypt)

    private String role; // e.g., "ROLE_USER" or "ROLE_ADMIN"
}