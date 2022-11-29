package org.kivilev.service;

import org.kivilev.model.Student;

public interface PollService {
    void printQuestionsAndAnswers();

    void doPoll(Student student);
}
