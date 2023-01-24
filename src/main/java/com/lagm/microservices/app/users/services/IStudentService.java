package com.lagm.microservices.app.users.services;

import com.lagm.microservices.app.users.models.entity.Student;

import java.util.Optional;

public interface IStudentService {
    Iterable<Student> findAll();
    Optional<Student> findById(Long id);
    Student save(Student student);
    void deleteById(Long id);
}
