package com.example.demo.model;

import com.example.demo.entity.Course;
import com.example.demo.repo.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Transactional
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
