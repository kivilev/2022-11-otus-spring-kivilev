package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.dao.StudentDao;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final InputStudentService inputStudentService;

    @Override
    public Student registrationStudent() {
        Student student = inputStudentService.inputStudent();
        studentDao.addStudent(student);
        return student;
    }
}
