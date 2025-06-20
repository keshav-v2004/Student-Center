package com.studentManagement.Student.Center.service;

import com.mongodb.DuplicateKeyException;
import com.studentManagement.Student.Center.entity.Student;
import com.studentManagement.Student.Center.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    //get all student
    public ResponseEntity<?> getAllStudents() {
        if (studentRepository.findAll().isEmpty()){
            return new ResponseEntity<>("no students added" , HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentRepository.findAll() , HttpStatus.OK);
    }

    //get student by id
    public ResponseEntity<?> getStudentById(String id) {

        Optional<Student> x = studentRepository.findAll().stream()
                .filter(student -> student.getRoll().equals(id))
                .findFirst();
        if (x.isPresent()){
            return new ResponseEntity<>(x,HttpStatus.OK);
        }
        else return new ResponseEntity<>("no such student with supplied id present",HttpStatus.NOT_FOUND);

    }
    // add new student
    public ResponseEntity<?> addStudent(Student student) {
        if (studentRepository.existsById(student.getRoll())) {
            return new ResponseEntity<>("Student with this roll number already exists", HttpStatus.CONFLICT);
        }

        try {
            studentRepository.save(student);
            return new ResponseEntity<>("Student added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //update a student by id
    public ResponseEntity<?> updateStudent(String id, Student updatedStudent) {

        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()) {
            // ensure ID is correct (since MongoDB uses it as key)
            updatedStudent.setRoll(id);
            studentRepository.save(updatedStudent);
            return new ResponseEntity<>("Updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }
    }

    //delete student
    public ResponseEntity<?> deleteStudent(String id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
        }

    }
}
