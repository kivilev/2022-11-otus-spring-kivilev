package org.kivilev.service;

import lombok.AllArgsConstructor;
import lombok.val;
import org.kivilev.model.PollResult;
import org.kivilev.model.Question;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InputPollServiceImpl implements InputPollService {

    private final IoStreamService ioStreamService;
    private final MessageSourceLocale messageSource;

    @Override
    public PollResult processQuestionsAndAnswers(List<Question> questions, int correctAnswerMinimumCount) {
        int correctAnswersCount = 0;
        int wrongAnswersCount = 0;

        for (var question : questions) {
            ioStreamService.println(question.getQuestionText());
            for (var answer : question.getAnswers()) {
                ioStreamService.println(String.valueOf(answer));
            }
            ioStreamService.print(messageSource.getMessage("enter.answers", new String[]{}));
            String correctAnswerPositions = ioStreamService.inputString();

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
