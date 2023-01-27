package com.lagm.microservices.app.users.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lagm.microservices.app.users.models.entity.Student;
import com.lagm.microservices.app.users.services.IStudentService;
import com.lagm.microservices.commons.controllers.CommonController;

@RestController
@RequestMapping
public class StudentController extends CommonController<Student, IStudentService> {

    private Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Student> optionalStudent = this.service.findById(id);

            if (optionalStudent.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Student studentDB = optionalStudent.get();
            studentDB.setLastname(student.getLastname());
            studentDB.setFirstname(student.getFirstname());
            studentDB.setEmail(student.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(studentDB));

        } catch (Exception e) {
        	LOGGER.error(e.getMessage());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
