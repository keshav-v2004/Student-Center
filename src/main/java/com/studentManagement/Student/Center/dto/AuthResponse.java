package com.studentManagement.Student.Center.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

}
