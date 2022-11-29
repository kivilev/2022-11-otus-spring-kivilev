package org.kivilev.service;

import lombok.val;
import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

@Service
public class PollPrintResultServiceImpl implements PollPrintResultService {
    @Override
    public void printResult(Student student, PollResult pollResult) {
        val passedResult = pollResult.isPassed() ? "passed" : "hasn't passed";
        System.out.printf("Student %s %s polls. Correct answers: %s. Wrong answers: %s",
                student.getName(),
                passedResult,
                pollResult.getCorrectAnswersCount(),
                pollResult.getWrongAnswersCount()
        );
    }
}
