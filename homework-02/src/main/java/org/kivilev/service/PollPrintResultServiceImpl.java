package org.kivilev.service;

import lombok.val;
import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class PollPrintResultServiceImpl implements PollPrintResultService {

    private final PrintStream printStream;

    public PollPrintResultServiceImpl(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void printResult(Student student, PollResult pollResult) {
        val passedResult = pollResult.isPassed() ? "passed" : "hasn't passed";
        printStream.printf("Student %s %s polls. Correct answers: %s. Wrong answers: %s",
                student.getName(),
                passedResult,
                pollResult.getCorrectAnswersCount(),
                pollResult.getWrongAnswersCount()
        );
    }
}
