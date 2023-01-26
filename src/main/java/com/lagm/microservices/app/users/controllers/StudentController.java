package com.lagm.microservices.app.users.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lagm.microservices.app.users.models.entity.Student;
import com.lagm.microservices.app.users.services.IStudentService;

@RestController
@RequestMapping
public class StudentController {

    private Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private IStudentService studentService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();

        try {
            return ResponseEntity.ok().body(this.studentService.findAll());

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Student> optionalStudent = this.studentService.findById(id);

            if (optionalStudent.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(optionalStudent.get());

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Student student) {
        Map<String, Object> response = new HashMap<>();

        try {
            Student studentDB = this.studentService.save(student);
            response.put("message", "Estudiante creado satisfactoriamente");
            response.put("student", studentDB);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Student student, @PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Optional<Student> optionalStudent = this.studentService.findById(id);

            if (optionalStudent.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Student studentDB = optionalStudent.get();
            studentDB.setLastname(student.getLastname());
            studentDB.setFirstname(student.getFirstname());
            studentDB.setEmail(student.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.studentService.save(studentDB));

        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Optional<Student> optionalStudent = this.studentService.findById(id);
            if (optionalStudent.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            this.studentService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (Exception e) {
            String message = e.getMessage();
            LOGGER.error(message, e);
            response.put("error", message);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
