package org.kivilev.service;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.StudentDao;
import org.kivilev.dao.StudentDaoInMemory;
import org.kivilev.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    private static final String STUDENT_NAME = "Ivan";
    private static final int STUDENT_AGE = 22;
    private static final long STUDENT_ID = 777L;

    private final StudentDao studentDao = mock(StudentDaoInMemory.class);
    private final InputStudentService inputStudentService = mock(InputStudentServiceImpl.class);
    private final StudentService studentService = new StudentServiceImpl(studentDao, inputStudentService);

    @Test
    public void registrationNewStudentShouldBeCorrect() {
        val newStudent = new Student(STUDENT_NAME, STUDENT_AGE);
        when(inputStudentService.inputStudent()).thenReturn(newStudent);
        when(studentDao.addStudent(newStudent)).thenReturn(STUDENT_ID);

        val student = studentService.registrationStudent();

        assertNotNull(student);
        assertEquals(STUDENT_NAME, student.getName());
        assertEquals(STUDENT_AGE, student.getAge());
        assertEquals(STUDENT_ID, student.getId());
    }
}