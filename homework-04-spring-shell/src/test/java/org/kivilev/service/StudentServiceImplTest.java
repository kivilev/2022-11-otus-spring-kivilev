package org.kivilev.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.StudentDao;
import org.kivilev.dao.StudentDaoInMemory;
import org.kivilev.dao.StudentIdGeneratorService;
import org.kivilev.model.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceImplTest {

    private static final String STUDENT_NAME = "Ivan";
    private static final int STUDENT_AGE = 22;
    private static final long STUDENT_ID = 777L;

    private final InputStudentService inputStudentService = mock(InputStudentServiceImpl.class);
    private final StudentIdGeneratorService studentIdGeneratorService = mock(StudentIdGeneratorService.class);
    private final StudentDao studentDao = new StudentDaoInMemory(studentIdGeneratorService);
    private final StudentService studentService = new StudentServiceImpl(studentDao, inputStudentService);

    @Test
    @DisplayName("Регистрация нового студента с корректными параметрами проходит успешно")
    public void registrationNewStudentWithCorrectParamsShouldBeCorrect() {
        val newStudent = new Student(STUDENT_NAME, STUDENT_AGE);
        when(inputStudentService.inputStudent()).thenReturn(newStudent);
        when(studentIdGeneratorService.getNewId()).thenReturn(STUDENT_ID);

        val student = studentService.registrationStudent();

        assertNotNull(student);
        assertEquals(STUDENT_NAME, student.getName());
        assertEquals(STUDENT_AGE, student.getAge());
        assertEquals(STUDENT_ID, student.getId());
    }
}