package com.example.demo;

import com.example.demo.model.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdminSetup {

    private final StudentService studentService;

    @Autowired
    public AdminSetup(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostConstruct
    public void init() {
        studentService.saveFirstAdmin();  // Automatically create the admin user on startup
    }
}
