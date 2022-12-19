package org.kivilev.service;

import org.kivilev.model.Question;
import org.springframework.stereotype.Service;

@Service
public class PrintQuestionServiceImpl implements PrintQuestionService {
    @Override
    public void printQuestion(Question question) {
        System.out.printf("Вопрос: %s. Правильный ответ(ы): %s. Все ответы: %s%n",
                question.getQuestionText(),
                question.getRightAnswersPositions(),
                question.getAnswers()
        );
    }
}
