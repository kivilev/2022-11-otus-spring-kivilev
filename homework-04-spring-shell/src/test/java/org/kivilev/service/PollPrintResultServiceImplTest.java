package org.kivilev.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@SpringBootTest
@DisplayName("Вывод результатов для студента ")
class PollPrintResultServiceImplTest {
    private static final Student STUDENT = new Student("Name", 22);
    private static final PollResult POLL_RESULT_PASSED = new PollResult(5, 2, true);
    private static final PollResult POLL_RESULT_NOT_PASSED = new PollResult(2, 5, false);

    private static final String PASSED_MESSAGE = "passed";
    private static final String NOT_PASSED_MESSAGE = "not passed";

    @Autowired
    PollPrintResultService pollPrintResultService;

    @MockBean
    IoStreamService ioStreamService;
    @MockBean
    MessageSourceLocale messageSource;

    @Test
    @DisplayName("не прошедшего тест происходит корректно")
    public void printResultForStudentWhoDidNotPassExamShouldBeCorrect() {
        Mockito.when(messageSource.getMessage(eq("recap.success"), any())).thenReturn(PASSED_MESSAGE);
        Mockito.when(messageSource.getMessage(eq("recap.fail"), any())).thenReturn(NOT_PASSED_MESSAGE);

        pollPrintResultService.printResult(STUDENT, POLL_RESULT_NOT_PASSED);

        Mockito.verify(ioStreamService, times(1)).print(NOT_PASSED_MESSAGE);
        Mockito.verify(ioStreamService, never()).print(PASSED_MESSAGE);
    }

    @Test
    @DisplayName("прошедшего тест происходит корректно")
    public void printResultForStudentWhoPassedExamShouldBeCorrect() {
        Mockito.when(messageSource.getMessage(eq("recap.success"), any())).thenReturn(PASSED_MESSAGE);
        Mockito.when(messageSource.getMessage(eq("recap.fail"), any())).thenReturn(NOT_PASSED_MESSAGE);

        pollPrintResultService.printResult(STUDENT, POLL_RESULT_PASSED);

        Mockito.verify(ioStreamService, times(1)).print(PASSED_MESSAGE);
        Mockito.verify(ioStreamService, never()).print(NOT_PASSED_MESSAGE);
    }
}