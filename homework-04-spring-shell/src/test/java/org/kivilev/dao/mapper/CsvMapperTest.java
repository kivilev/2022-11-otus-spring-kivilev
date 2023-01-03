package org.kivilev.dao.mapper;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.dto.QuestionAnswersDto;
import org.kivilev.model.Answer;
import org.kivilev.model.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Маппинг CSV строчки ")
class CsvMapperTest {
    private static final String QUESTION_TEST = "question-text";
    private static final String CORRECT_ANSWER_POSITIONS = "0,1";
    private static final String ANSWER1 = "answer1-text";
    private static final String ANSWER2 = "answer2-text";
    private static final String ANSWER3 = "answer3-text";
    private static final String ANSWER4 = "answer4-text";
    private static final String ANSWER5 = "answer5-text";
    private static final String ANSWER6 = "answer6-text";

    private final CsvMapper mapper = new CsvMapper();

    @Test
    @DisplayName("со всеми заполненными ответами должен быть корректен")
    public void mappingCsvRowWithAllFilledAnswersToQuestionAnswersShouldBeCorrect() {
        final int answersCount = 6;
        final int correctAnswersCount = 2;
        final List<Integer> correctAnswerPositionsList = List.of(0, 1);

        QuestionAnswersDto questionAnswersDto = new QuestionAnswersDto(
                QUESTION_TEST, CORRECT_ANSWER_POSITIONS,
                ANSWER1, ANSWER2, ANSWER3, ANSWER4, ANSWER5, ANSWER6);

        val question = mapper.fromDto(questionAnswersDto);

        assertNotNull(question);
        assertEquals(QUESTION_TEST, question.getQuestionText());

        assertEquals(answersCount, question.getAnswers().size());
        assertEquals(ANSWER1, getAnswerByPosition(question, 0));
        assertEquals(ANSWER2, getAnswerByPosition(question, 1));
        assertEquals(ANSWER3, getAnswerByPosition(question, 2));
        assertEquals(ANSWER4, getAnswerByPosition(question, 3));
        assertEquals(ANSWER5, getAnswerByPosition(question, 4));
        assertEquals(ANSWER6, getAnswerByPosition(question, 5));

        assertEquals(correctAnswersCount, question.getRightAnswersPositions().size());
        assertEquals(correctAnswerPositionsList, question.getRightAnswersPositions());
    }

    @Test
    @DisplayName("с двумя заполненными ответами должен быть корректен")
    public void mappingCsvRowWithTwoAnswersToQuestionAnswersShouldBeCorrect() {
        final int answersCount = 2;
        final int correctAnswersCount = 2;
        final List<Integer> correctAnswerPositionsList = List.of(0, 1);

        QuestionAnswersDto questionAnswersDto = new QuestionAnswersDto(
                QUESTION_TEST, CORRECT_ANSWER_POSITIONS,
                ANSWER1, ANSWER2, "", "", "", "");

        val question = mapper.fromDto(questionAnswersDto);

        assertNotNull(question);
        assertEquals(QUESTION_TEST, question.getQuestionText());

        assertEquals(answersCount, question.getAnswers().size());
        assertEquals(ANSWER1, getAnswerByPosition(question, 0));
        assertEquals(ANSWER2, getAnswerByPosition(question, 1));
        assertNull(getAnswerByPosition(question, 2));
        assertNull(getAnswerByPosition(question, 3));
        assertNull(getAnswerByPosition(question, 4));
        assertNull(getAnswerByPosition(question, 5));

        assertEquals(correctAnswersCount, question.getRightAnswersPositions().size());
        assertEquals(correctAnswerPositionsList, question.getRightAnswersPositions());
    }

    private String getAnswerByPosition(Question question, int position) {
        return question.getAnswer(position)
                .map(Answer::getText)
                .orElse(null);
    }
}