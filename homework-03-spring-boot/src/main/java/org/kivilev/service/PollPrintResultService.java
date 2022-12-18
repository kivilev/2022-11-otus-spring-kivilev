package org.kivilev.service;

import org.kivilev.model.PollResult;
import org.kivilev.model.Student;

public interface PollPrintResultService {
    void printResult(Student student, PollResult pollResult);
}
