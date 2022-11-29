package org.kivilev.service;

import lombok.val;
import org.kivilev.dao.StudentDao;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final InputStudentService inputStudentService;

    public StudentServiceImpl(StudentDao studentDao, InputStudentService inputStudentService) {
        this.studentDao = studentDao;
        this.inputStudentService = inputStudentService;
    }

    @Override
    public Student registrationStudent() {
        Student student = inputStudentService.inputStudent();
        val studentId = studentDao.addStudent(student);
        student.setId(studentId);
        return student;
    }
}
