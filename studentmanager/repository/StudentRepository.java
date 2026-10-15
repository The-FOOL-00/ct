package com.example.studentmanager.repository;

import com.example.studentmanager.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository
        extends MongoRepository<Student, String> {
}