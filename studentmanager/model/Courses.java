package com.example.studentmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Courses")
public class Course {

    @Id
    private String id;

    private String courseName;
    private int credits;
}