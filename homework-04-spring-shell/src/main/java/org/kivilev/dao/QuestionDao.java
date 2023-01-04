package org.kivilev.dao;

import org.kivilev.model.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> getQuestions();
}
