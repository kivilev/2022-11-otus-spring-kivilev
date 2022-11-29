package org.kivilev.dao;

import lombok.val;
import org.kivilev.model.Student;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class StudentDaoInMemory implements StudentDao {

    private final AtomicLong currentId = new AtomicLong(0);
    private final ConcurrentHashMap<Long, Student> studentConcurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public Optional<Student> getStudent(long id) {
        return Optional.ofNullable(studentConcurrentHashMap.get(id));
    }

    @Override
    public long addStudent(Student student) {
        val studentId = currentId.incrementAndGet();
        student.setId(studentId);
        studentConcurrentHashMap.put(studentId, student);
        return studentId;
    }
}