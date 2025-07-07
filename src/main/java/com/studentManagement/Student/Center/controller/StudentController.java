package com.studentManagement.Student.Center.controller;

import com.studentManagement.Student.Center.entity.Student;
import com.studentManagement.Student.Center.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    //get all student
    @GetMapping("/allStudent")
    public ResponseEntity<?> getAllStudent(){
        return studentService.getAllStudents();
    }

    //get student by id
    @GetMapping("/getById/{myId}")
    public ResponseEntity<?> getStudentById(@PathVariable String myId){
        return studentService.getStudentById(myId);
    }

    // add new student
    @PostMapping("/addNewStudent")
    public ResponseEntity<?> addNewStudent(@Valid @RequestBody Student student){
        return studentService.addStudent(student);
    }

    //update a student by id
    @PutMapping("/updateById/{myId}")
    public ResponseEntity<?> updateById(@PathVariable String myId , @RequestBody Student student){
        return studentService.updateStudent(myId, student);
    }

    //delete student
    @DeleteMapping("/deleteStudentById/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId){
        return studentService.deleteStudent(myId);
    }

    //find all students of a particular branch
    @GetMapping("/findByBranch/{myBranch}")
    public List<Student> findByBranch(@PathVariable String myBranch){
        return studentService.findByBranch(myBranch);
    }


    //find students by name
    @GetMapping("/findByName/{myName}")
    public List<Student> findByName(@PathVariable String myName){
        return studentService.findByName(myName);
    }

    @GetMapping("/paginated")
    public Page<Student> getStudentsPaginated(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        return studentService.getPaginatedStudents(page, size, sortBy);
    }


}
