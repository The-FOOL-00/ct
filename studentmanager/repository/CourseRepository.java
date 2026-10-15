package com.example.studentmanager.repository;

import com.example.studentmanager.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository
        extends MongoRepository<Course, String> {
}