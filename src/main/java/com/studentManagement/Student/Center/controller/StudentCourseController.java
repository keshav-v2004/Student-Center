package com.studentManagement.Student.Center.controller;

import com.studentManagement.Student.Center.entity.Course;
import com.studentManagement.Student.Center.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students/{roll}/courses")
public class StudentCourseController {

    @Autowired
    private StudentService studentService;

    // Get all courses for a student
    @GetMapping
    public ResponseEntity<?> getAllCourses(@PathVariable String roll) {
        return studentService.getCoursesOfStudent(roll);
    }

    //  Add course to a student
    @PostMapping
    public ResponseEntity<?> addCourse(@PathVariable String roll, @RequestBody Course course) {
        return studentService.addCourseToStudent(roll, course);
    }

    // Delete a course
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable String roll, @PathVariable String courseId) {
        return studentService.removeCourseFromStudent(roll, courseId);
    }

    //  Update course
    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(
            @PathVariable String roll,
            @PathVariable String courseId,
            @RequestBody Course updatedCourse) {
        return studentService.updateCourseOfStudent(roll, courseId, updatedCourse);
    }
}
