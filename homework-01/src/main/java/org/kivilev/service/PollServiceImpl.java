package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.dao.QuestionDao;

@AllArgsConstructor
public class PollServiceImpl implements PollService {

    private final QuestionDao questionDao;
    private final PrintQuestionService printQuestionService;

    @Override
    public void printQuestionsAndAnswers() {
        var questions = questionDao.getQuestions();
        questions.forEach(printQuestionService::printQuestion);
    }
}
