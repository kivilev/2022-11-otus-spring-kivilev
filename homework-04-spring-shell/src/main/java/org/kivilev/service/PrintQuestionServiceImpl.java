package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.model.Question;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrintQuestionServiceImpl implements PrintQuestionService {

    private final OutputStreamData outputStreamData;
    private final MessageSourceLocale messageSourceLocale;

    @Override
    public void printQuestion(Question question) {
        outputStreamData.println(
                messageSourceLocale.getMessage("recap.stats",
                        new Object[]{question.getQuestionText(),
                                question.getRightAnswersPositions(),
                                question.getAnswers()}));
    }
}
