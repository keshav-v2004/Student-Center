package com.studentManagement.Student.Center.dto;

import lombok.Data;


// this will be used for SignUp

@Data
public class RegisterRequest {

    private String username;
    private String email;
    private String password;

    private String role = "ROLE_USER";  // "ROLE_USER" only admins can create new admins

}
