package org.kivilev.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DisplayName("Ввод студента ")
class InputStudentServiceImplTest {

    private static final String STUDENT_NAME = "Name";
    private static final int STUDENT_AGE = 22;
    private static final int WRONG_STUDENT_AGE = -22;


    @Autowired
    InputStudentService inputStudentService;
    @MockBean
    IoStreamService ioStreamService;

    @Test
    @DisplayName("с корректными параметрами проходит успешно")
    public void inputStudentWithCorrectDataShouldBeSuccessful() {
        Mockito.when(ioStreamService.inputString()).thenReturn(STUDENT_NAME);
        Mockito.when(ioStreamService.inputInt()).thenReturn(STUDENT_AGE);

        var actualStudent = inputStudentService.inputStudent();

        assertNotNull(actualStudent);
        assertEquals(STUDENT_NAME, actualStudent.getName());
        assertEquals(STUDENT_AGE, actualStudent.getAge());
    }

    @Test
    @DisplayName("с некорректным возрастом должен выбросить исключение")
    public void inputStudentWithIncorrectAgeDataShouldThrowException() {
        Mockito.when(ioStreamService.inputString()).thenReturn(STUDENT_NAME);
        Mockito.when(ioStreamService.inputInt()).thenReturn(WRONG_STUDENT_AGE);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inputStudentService.inputStudent();
        });

        assertEquals("Invalid age", exception.getMessage());
    }

    @Test
    @DisplayName("с незаданным именем должен выбросить исключение")
    public void inputStudentWithNotDefinedNameDataShouldThrowException() {
        Mockito.when(ioStreamService.inputString()).thenReturn(null);
        Mockito.when(ioStreamService.inputInt()).thenReturn(STUDENT_AGE);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            inputStudentService.inputStudent();
        });

        assertEquals("Invalid name", exception.getMessage());
    }

}