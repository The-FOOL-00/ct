package com.example.studentmanager.controller;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.Course;
import com.example.studentmanager.service.StudentService;
import com.example.studentmanager.service.CourseService;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ManagementController {

    private final StudentService studentService;
    private final CourseService courseService;

    public ManagementController(
            StudentService studentService,
            CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    // STUDENT CRUD

    @GetMapping("/api/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @PostMapping("/api/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping("/api/students/{id}")
    public Student updateStudent(
            @PathVariable String id,
            @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/api/students/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return "Student deleted";
    }

    // COURSE CRUD

    @GetMapping("/api/courses")
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @PostMapping("/api/courses")
    public Course addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @PutMapping("/api/courses/{id}")
    public Course updateCourse(
            @PathVariable String id,
            @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/api/courses/{id}")
    public String deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return "Course deleted";
    }
}