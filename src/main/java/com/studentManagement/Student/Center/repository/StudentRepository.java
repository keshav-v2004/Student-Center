package com.studentManagement.Student.Center.repository;

import com.studentManagement.Student.Center.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student,String> {

    // Find all students by branch
    List<Student> findByBranch(String branch);

    // Find students by name (exact match)
    List<Student> findByName(String name);

    Page<Student> findAll(Pageable pageable);

}
