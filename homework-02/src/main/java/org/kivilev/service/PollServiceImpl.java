package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.config.ApplicationConfig;
import org.kivilev.dao.QuestionDao;
import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PollServiceImpl implements PollService {

    private final QuestionDao questionDao;
    private final PrintQuestionService printQuestionService;
    private final InputPollService inputPollService;
    private final ApplicationConfig applicationConfig;
    private final PollPrintResultService pollPrintResultService;

    @Override
    public void printQuestionsAndAnswers() {
        var questions = questionDao.getQuestions();
        questions.forEach(printQuestionService::printQuestion);
    }

    @Override
    public void doPoll(Student student) {
        var questions = questionDao.getQuestions();
        PollResult pollResult = inputPollService.processQuestionsAndAnswers(questions, applicationConfig.getCorrectAnswersMinimumCount());
        pollPrintResultService.printResult(student, pollResult);
    }
}