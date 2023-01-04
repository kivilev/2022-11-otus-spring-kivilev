package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PollPrintResultServiceImpl implements PollPrintResultService {

    private final OutputStreamData outputStreamData;
    private final MessageSourceLocale messageSource;

    @Override
    public void printResult(Student student, PollResult pollResult) {
        var params = new String[]{
                student.getName(),
                String.valueOf(pollResult.getCorrectAnswersCount()),
                String.valueOf(pollResult.getWrongAnswersCount())};

        if (pollResult.isPassed()) {
            outputStreamData.print(messageSource.getMessage("recap.success", params));
        } else {
            outputStreamData.print(messageSource.getMessage("recap.fail", params));
        }
    }
}