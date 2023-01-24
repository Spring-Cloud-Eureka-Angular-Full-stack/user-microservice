package com.lagm.microservices.app.users.models.repository;

import com.lagm.microservices.app.users.models.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
