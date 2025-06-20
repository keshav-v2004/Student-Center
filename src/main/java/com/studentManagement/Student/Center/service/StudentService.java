package com.studentManagement.Student.Center.service;

import com.studentManagement.Student.Center.entity.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentService {


    public List<Student> studentList = new ArrayList<>();

    //get all student
    public List<Student> getAllStudents() {
        return studentList;
    }

    //get student by id
    public Student getStudentById(String id) {

        return studentList.stream()
                .filter(student -> student.getRoll().equals(id))
                .findFirst()
                .orElse(null);
    }

    // add new student
    public void addStudent(Student student) {
        studentList.add(student);
    }

    //update a student by id
    public boolean updateStudent(String id, Student updatedStudent) {
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            if (s.getRoll().equals(id)) {
                studentList.set(i, updatedStudent);
                return true;
            }
        }
        return false;
    }

    //delete student
    public boolean deleteStudent(String id) {
        return studentList.removeIf(student -> student.getRoll().equals(id));
    }




}
