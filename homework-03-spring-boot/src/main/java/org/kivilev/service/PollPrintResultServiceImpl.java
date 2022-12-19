package org.kivilev.service;

import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class PollPrintResultServiceImpl implements PollPrintResultService {

    private final PrintStream printStream;
    private final MessageSourceLocale messageSource;

    public PollPrintResultServiceImpl(PrintStream printStream, MessageSourceLocale messageSource) {
        this.printStream = printStream;
        this.messageSource = messageSource;
    }

    @Override
    public void printResult(Student student, PollResult pollResult) {
        var params = new String[]{
                student.getName(),
                String.valueOf(pollResult.getCorrectAnswersCount()),
                String.valueOf(pollResult.getWrongAnswersCount())};

        if (pollResult.isPassed()) {
            printStream.print(messageSource.getMessage("recap.success", params));
        } else {
            printStream.print(messageSource.getMessage("recap.fail", params));
        }
    }
}