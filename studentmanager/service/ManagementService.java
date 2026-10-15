package com.example.studentmanager.service;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.model.Course;

import com.example.studentmanager.repository.StudentRepository;
import com.example.studentmanager.repository.CourseRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManagementService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;

    public ManagementService(
            StudentRepository studentRepo,
            CourseRepository courseRepo) {

        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    // ================= STUDENT CRUD =================

    // READ ALL STUDENTS
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    // CREATE STUDENT
    public Student addStudent(Student student) {
        student.setId(null);
        return studentRepo.save(student);
    }

    // UPDATE STUDENT
    public Student updateStudent(String id, Student updated) {

        Student student = studentRepo.findById(id).orElse(null);

        if (student == null) {
            return null;
        }

        student.setName(updated.getName());
        student.setDepartment(updated.getDepartment());
        student.setYear(updated.getYear());
        student.setCourseIds(updated.getCourseIds());

        return studentRepo.save(student);
    }

    // DELETE STUDENT
    public void deleteStudent(String id) {
        studentRepo.deleteById(id);
    }


    // ================= COURSE CRUD =================

    // READ ALL COURSES
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }

    // CREATE COURSE
    public Course addCourse(Course course) {
        course.setId(null);
        return courseRepo.save(course);
    }

    // UPDATE COURSE
    public Course updateCourse(String id, Course updated) {

        Course course = courseRepo.findById(id).orElse(null);

        if (course == null) {
            return null;
        }

        course.setCourseName(updated.getCourseName());
        course.setCredits(updated.getCredits());

        return courseRepo.save(course);
    }

    // DELETE COURSE
    public void deleteCourse(String id) {
        courseRepo.deleteById(id);
    }
}