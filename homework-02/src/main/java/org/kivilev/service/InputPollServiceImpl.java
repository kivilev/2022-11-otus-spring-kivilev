package org.kivilev.service;

import lombok.val;
import org.kivilev.model.PollResult;
import org.kivilev.model.Question;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class InputPollServiceImpl implements InputPollService {

    private final Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    @Override
    public PollResult processQuestionsAndAnswers(List<Question> questions, int correctAnswerMinimumCount) {
        int correctAnswersCount = 0;
        int wrongAnswersCount = 0;

        for (var question : questions) {
            System.out.println(question.getQuestionText());
            for (var answer : question.getAnswers()) {
                System.out.println(answer);
            }
            System.out.print("Enter nums of correct answers with commas [1,2 or 0]: ");
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
