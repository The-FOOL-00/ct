package com.example.studentmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Students")
public class Student {

    @Id
    private String id;

    private String name;
    private String department;
    private int year;
}