package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.model.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){

        List<Student> student = studentService.getAllStudents();

        if (student != null && !student.isEmpty()){
            return new ResponseEntity<>(student, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Endpoint to create an admin user manually
    @PostMapping("/create-admin")
    public String createAdminUser(@RequestParam String username, @RequestParam String password, @RequestParam String name)
    {
        studentService.createAdmin(username, password, name, List.of("ADMIN").toArray(new String[0]));
        return "Admin user created successfully!";
    }
}
