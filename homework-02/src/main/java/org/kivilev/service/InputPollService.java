package org.kivilev.service;

import org.kivilev.model.PollResult;
import org.kivilev.model.Question;

import java.util.List;

public interface InputPollService {
    PollResult processQuestionsAndAnswers(List<Question> questions, int correctAnswerMinimumCount);
}
