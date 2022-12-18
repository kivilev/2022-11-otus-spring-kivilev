package org.kivilev.service;

import lombok.val;
import org.kivilev.config.PollsProperties;
import org.kivilev.model.PollResult;
import org.kivilev.model.Question;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class InputPollServiceImpl implements InputPollService {

    private final Scanner scanner;
    private final PrintStream printStream;
    private final PollsProperties pollsProperties;
    private final MessageSource messageSource;

    public InputPollServiceImpl(InputStream inputStream, PrintStream printStream, PollsProperties pollsProperties, MessageSource messageSource) {
        this.scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        this.printStream = printStream;
        this.pollsProperties = pollsProperties;
        this.messageSource = messageSource;
    }

    @Override
    public PollResult processQuestionsAndAnswers(List<Question> questions, int correctAnswerMinimumCount) {
        int correctAnswersCount = 0;
        int wrongAnswersCount = 0;

        for (var question : questions) {
            printStream.println(question.getQuestionText());
            for (var answer : question.getAnswers()) {
                printStream.println(answer);
            }
            printStream.print(messageSource.getMessage("enter.answers", new String[]{}, pollsProperties.getLocale()));
            String correctAnswerPositions = scanner.nextLine();

            val answerPositions = Arrays.stream(correctAnswerPositions.split(","))
                    .map(String::trim)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            val isCorrectAnswers = question.getRightAnswersPositions().size() == answerPositions.size() && question.getRightAnswersPositions().containsAll(answerPositions);
            if (isCorrectAnswers) {
                correctAnswersCount++;
            } else {
                wrongAnswersCount++;
            }
        }
        return new PollResult(correctAnswersCount, wrongAnswersCount, correctAnswersCount >= correctAnswerMinimumCount);
    }
}
