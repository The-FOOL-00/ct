package com.example.studentmanager.service;

import com.example.studentmanager.model.Course;
import com.example.studentmanager.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    // READ ALL
    public List<Course> getCourses() {
        return repository.findAll();
    }

    // CREATE
    public Course addCourse(Course course) {
        course.setId(null);
        return repository.save(course);
    }

    // UPDATE
    public Course updateCourse(String id, Course updated) {
        Course course = repository.findById(id).orElse(null);

        if (course == null) return null;

        course.setCourseName(updated.getCourseName());
        course.setCredits(updated.getCredits());

        return repository.save(course);
    }

    // DELETE
    public void deleteCourse(String id) {
        repository.deleteById(id);
    }
}