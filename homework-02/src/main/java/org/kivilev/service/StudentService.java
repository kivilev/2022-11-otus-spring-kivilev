package org.kivilev.service;

import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {
    Student registrationStudent();
}
