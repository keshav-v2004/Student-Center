package com.studentManagement.Student.Center.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Course {

    @NotBlank(message = "Course ID is required")
    private String courseId;

    @NotBlank(message = "Course title is required")
    private String title;

    @NotBlank(message = "Course duration is required")
    private String duration;
}
