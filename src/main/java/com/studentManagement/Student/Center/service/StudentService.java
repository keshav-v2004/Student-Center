package com.studentManagement.Student.Center.service;

import com.studentManagement.Student.Center.entity.Course;
import com.studentManagement.Student.Center.entity.Student;
import com.studentManagement.Student.Center.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
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

    // find all students by branch
    public List<Student> findByBranch(String branch){
        return studentRepository.findByBranch(branch);
    }

    // find by name
    public List<Student> findByName(String name){
        return studentRepository.findByName(name);
    }

    //get paginated students
    public Page<Student> getPaginatedStudents(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return studentRepository.findAll(pageable);
    }

    // courses added for each student form here

    //get all courses of a student
    public ResponseEntity<?> getCoursesOfStudent(String roll) {
        Optional<Student> studentOpt = studentRepository.findById(roll);

        if (studentOpt.isPresent()) {
            return ResponseEntity.ok(studentOpt.get().getCourses());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with roll " + roll + " not found");
        }
    }

    // add a course to a student
    public ResponseEntity<?> addCourseToStudent(String roll, Course course) {
        Optional<Student> studentOpt = studentRepository.findById(roll);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        Student student = studentOpt.get();
        boolean exists = student.getCourses().stream()
                .anyMatch(c -> c.getCourseId().equals(course.getCourseId()));

        if (exists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Course with ID " + course.getCourseId() + " already exists");
        }

        student.getCourses().add(course);
        studentRepository.save(student);

        return ResponseEntity.ok("Course added successfully");
    }

    // remove a course from a student
    public ResponseEntity<?> removeCourseFromStudent(String roll, String courseId) {
        Optional<Student> studentOpt = studentRepository.findById(roll);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        Student student = studentOpt.get();

        boolean removed = student.getCourses()
                .removeIf(c -> c.getCourseId().equals(courseId));

        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
        }

        studentRepository.save(student);
        return ResponseEntity.ok("Course removed successfully");
    }


    // update course of a student
    public ResponseEntity<?> updateCourseOfStudent(String roll, String courseId, Course updatedCourse) {
        Optional<Student> studentOpt = studentRepository.findById(roll);

        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        Student student = studentOpt.get();
        List<Course> courses = student.getCourses();

        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId().equals(courseId)) {
                courses.set(i, updatedCourse);
                studentRepository.save(student);
                return ResponseEntity.ok("Course updated successfully");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
    }
}
