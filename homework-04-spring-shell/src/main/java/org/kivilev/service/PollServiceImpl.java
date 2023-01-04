package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.config.PollsProperties;
import org.kivilev.dao.QuestionDao;
import org.kivilev.model.PollResult;
import org.kivilev.model.Student;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PollServiceImpl implements PollService {

    private final QuestionDao questionDao;
    private final InputPollService inputPollService;
    private final PollsProperties pollsProperties;
    private final PollPrintResultService pollPrintResultService;

    @Override
    public void doPoll(Student student) {
        var questions = questionDao.getQuestions();
        PollResult pollResult = inputPollService.processQuestionsAndAnswers(questions, pollsProperties.getCorrectAnswersMinimumCount());
        pollPrintResultService.printResult(student, pollResult);
    }
}