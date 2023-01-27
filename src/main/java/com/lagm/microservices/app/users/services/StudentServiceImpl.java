package com.lagm.microservices.app.users.services;

import org.springframework.stereotype.Service;

import com.lagm.microservices.app.users.models.entity.Student;
import com.lagm.microservices.app.users.models.repository.StudentRepository;
import com.lagm.microservices.commons.services.CommonServiceImpl;

@Service
public class StudentServiceImpl extends CommonServiceImpl<Student, StudentRepository> implements IStudentService {
	
}
