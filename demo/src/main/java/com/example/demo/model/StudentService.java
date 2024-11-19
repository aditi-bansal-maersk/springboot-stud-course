package com.example.demo.model;

import com.example.demo.entity.Student;
import com.example.demo.repo.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll().stream()
                .filter(student -> student.getRoles().contains("USER")) // Filter by "USER" role
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    @Transactional
    public Student saveStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRoles(List.of("USER"));
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student getStudentByUsername(String username) {
        return studentRepository.findByUsername(username);  // Get student by username
    }

    public void createAdmin(String username, String password, String name, String[] roles) {

        Student student = new Student();
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode(password));
        student.setName(name);
        student.setRoles(Arrays.asList(roles));
        studentRepository.save(student);  // Save the student to the database
    }

    public void saveFirstAdmin() {
        String username = "admin";
        String password = "admin123";
        String name = "Admin User";
        String[] roles = {"ADMIN"};

        createAdmin(username, password, name, roles);
    }
}
