package com.studentManagement.Student.Center.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Document(collection = "students")
public class Student {

    @Id
    private String roll;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String name;

    @NotBlank(message = "Branch must not be blank")
    private String branch;

    @Valid
    private List<Course> courses = new ArrayList<>();
}
