package com.studentManagement.Student.Center.repository;

import com.studentManagement.Student.Center.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student,String> {
}
