package org.kivilev.dao;

import org.kivilev.model.Student;

import java.util.Optional;

public interface StudentDao {
    Optional<Student> getStudent(long id);

    long addStudent(Student student);
}
