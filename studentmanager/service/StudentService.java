package com.example.studentmanager.service;

import com.example.studentmanager.model.Student;
import com.example.studentmanager.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // READ ALL
    public List<Student> getStudents() {
        return repository.findAll();
    }

    // CREATE
    public Student addStudent(Student student) {
        student.setId(null);
        return repository.save(student);
    }

    // UPDATE
    public Student updateStudent(String id, Student updated) {
        Student student = repository.findById(id).orElse(null);

        if (student == null) return null;
        
        student.setName(updated.getName());
        student.setDepartment(updated.getDepartment());
        student.setYear(updated.getYear());
        student.setCourseIds(updated.getCourseIds());

        return repository.save(student);
    }

    // DELETE
    public void deleteStudent(String id) {
        repository.deleteById(id);
    }
}