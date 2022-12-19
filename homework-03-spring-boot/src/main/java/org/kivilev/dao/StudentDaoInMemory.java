package org.kivilev.dao;

import lombok.val;
import org.kivilev.model.Student;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class StudentDaoInMemory implements StudentDao {

    private final StudentIdGeneratorService studentIdGeneratorService;
    private final ConcurrentMap<Long, Student> studentConcurrentHashMap = new ConcurrentHashMap<>();

    public StudentDaoInMemory(StudentIdGeneratorService studentIdGeneratorService) {
        this.studentIdGeneratorService = studentIdGeneratorService;
    }

    @Override
    public Optional<Student> getStudent(long id) {
        return Optional.ofNullable(studentConcurrentHashMap.get(id));
    }

    @Override
    public void addStudent(Student student) {
        val studentId = studentIdGeneratorService.getNewId();
        student.setId(studentId);
        studentConcurrentHashMap.put(studentId, student);
    }
}