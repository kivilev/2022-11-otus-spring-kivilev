package org.kivilev.service;

import org.kivilev.config.PollsProperties;
import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class PollPrintResultServiceImpl implements PollPrintResultService {

    private final PrintStream printStream;
    private final MessageSource messageSource;
    private final PollsProperties pollsProperties;

    public PollPrintResultServiceImpl(PrintStream printStream, MessageSource messageSource, PollsProperties pollsProperties) {
        this.printStream = printStream;
        this.messageSource = messageSource;
        this.pollsProperties = pollsProperties;
    }

    @Override
    public void printResult(Student student, PollResult pollResult) {
        var params = new String[]{
                student.getName(),
                String.valueOf(pollResult.getCorrectAnswersCount()),
                String.valueOf(pollResult.getWrongAnswersCount())};

        if (pollResult.isPassed()) {
            printStream.print(messageSource.getMessage("recap.success", params, pollsProperties.getLocale()));
        } else {
            printStream.print(messageSource.getMessage("recap.fail", params, pollsProperties.getLocale()));
        }
    }
}
