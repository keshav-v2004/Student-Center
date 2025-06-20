package com.studentManagement.Student.Center.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "students")
public class Student {

    @Id
    private String roll;

    @NonNull
    private String name;

    @NonNull
    private String branch;


}
