package com.studentManagement.Student.Center.controller;

import com.studentManagement.Student.Center.entity.Student;
import com.studentManagement.Student.Center.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    //get all student
    @GetMapping("/allStudent")
    public List<Student> getAllStudent(){
        return studentService.getAllStudents();
    }

    //get student by id
    @GetMapping("/getById/{myId}")
    public Student getStudentById(@PathVariable String myId){
        return studentService.getStudentById(myId);
    }

    // add new student
    @PostMapping("/addNewStudent")
    public void addNewStudent(@RequestBody Student student){
        studentService.addStudent(student);
    }

    //update a student by id
    @PutMapping("/updateById/{myId}")
    public boolean updateById(@PathVariable String myId , @RequestBody Student student){
        return studentService.updateStudent(myId, student);
    }

    //delete student
    @DeleteMapping("/deleteStudentById/{myId}")
    public boolean deleteById(@PathVariable String myId){
        return studentService.deleteStudent(myId);
    }

}
