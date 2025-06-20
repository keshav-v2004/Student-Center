package com.studentManagement.Student.Center.service;

import com.studentManagement.Student.Center.entity.Student;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class StudentService {


    public List<Student> studentList = new ArrayList<>();

    //get all student
    public ResponseEntity<?> getAllStudents() {
        if (studentList.isEmpty()){
            return new ResponseEntity<>("no students added" , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentList , HttpStatus.OK);
    }

    //get student by id
    public ResponseEntity<?> getStudentById(String id) {

        Optional<Student> x = studentList.stream()
                .filter(student -> student.getRoll().equals(id))
                .findFirst();
        if (x.isPresent()){
            return new ResponseEntity<>(x,HttpStatus.OK);
        }
        else return new ResponseEntity<>("no such student with supplied id present",HttpStatus.NOT_FOUND);

    }

    // add new student
    public ResponseEntity<?> addStudent(Student student) {
        try {
            studentList.add(student);
            return new ResponseEntity<>("successfully added", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    //update a student by id
    public ResponseEntity<?> updateStudent(String id, Student updatedStudent) {
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            if (s.getRoll().equals(id)) {
                studentList.set(i, updatedStudent);
                return new ResponseEntity<>("updated successfully" , HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Something went wrong" , HttpStatus.BAD_REQUEST);
    }

    //delete student
    public ResponseEntity<?> deleteStudent(String id) {

        try {
            boolean isRemoved = studentList.removeIf(student -> student.getRoll().equals(id));
            if (isRemoved){
                return new ResponseEntity<>("deleted successfully" , HttpStatus.OK);
            }
            else return new ResponseEntity<>("no student with supplied id present" , HttpStatus.BAD_REQUEST);
        } catch (Exception e) {}

        return new ResponseEntity<>("something went wrong" , HttpStatus.BAD_REQUEST);

    }




}
