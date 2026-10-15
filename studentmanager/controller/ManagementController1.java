package com.example.studentmanager.controller;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.Course;
import com.example.studentmanager.service.ManagementService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ManagementController {

    private final ManagementService service;

    public ManagementController(ManagementService service) {
        this.service = service;
    }

    // ================= STUDENT CRUD =================

    // GET ALL STUDENTS
    @GetMapping("/api/students")
    public List<Student> getStudents() {
        return service.getStudents();
    }

    // ADD STUDENT
    @PostMapping("/api/students")
    public Student addStudent(@RequestBody Student student) {
        return service.addStudent(student);
    }

    // UPDATE STUDENT
    @PutMapping("/api/students/{id}")
    public Student updateStudent(
            @PathVariable String id,
            @RequestBody Student student) {

        return service.updateStudent(id, student);
    }

    // DELETE STUDENT
    @DeleteMapping("/api/students/{id}")
    public String deleteStudent(@PathVariable String id) {
        service.deleteStudent(id);
        return "Student deleted successfully";
    }


    // ================= COURSE CRUD =================

    // GET ALL COURSES
    @GetMapping("/api/courses")
    public List<Course> getCourses() {
        return service.getCourses();
    }

    // ADD COURSE
    @PostMapping("/api/courses")
    public Course addCourse(@RequestBody Course course) {
        return service.addCourse(course);
    }

    // UPDATE COURSE
    @PutMapping("/api/courses/{id}")
    public Course updateCourse(
            @PathVariable String id,
            @RequestBody Course course) {

        return service.updateCourse(id, course);
    }

    // DELETE COURSE
    @DeleteMapping("/api/courses/{id}")
    public String deleteCourse(@PathVariable String id) {
        service.deleteCourse(id);
        return "Course deleted successfully";
    }
}