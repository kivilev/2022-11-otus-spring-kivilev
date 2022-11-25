package org.kivilev.service;

import org.junit.jupiter.api.Test;
import org.kivilev.dao.QuestionDao;
import org.kivilev.dao.QuestionDaoCsvImpl;
import org.kivilev.model.Answer;
import org.kivilev.model.Question;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PollServiceImplTest {

    QuestionDao questionDao = mock(QuestionDaoCsvImpl.class);
    PrintQuestionService printQuestionService = mock(PrintQuestionServiceImpl.class);
    PollService pollService = new PollServiceImpl(questionDao, printQuestionService);

    @Test
    public void processingEmptyQuestionsShouldBeCorrect() {
        Mockito.when(questionDao.getQuestions()).thenReturn(new ArrayList<>());

        pollService.printQuestionsAndAnswers();

        verify(printQuestionService, never()).printQuestion(any());
    }

    @Test
    public void processingListWithOneQuestionShouldBeCorrect() {
        Question question = new Question("questionText", List.of(new Answer(0, "text")), List.of(0));
        List<Question> questionList = List.of(question);
        Mockito.when(questionDao.getQuestions()).thenReturn(questionList);

        pollService.printQuestionsAndAnswers();

        verify(printQuestionService, times(1)).printQuestion(question);
    }
}
