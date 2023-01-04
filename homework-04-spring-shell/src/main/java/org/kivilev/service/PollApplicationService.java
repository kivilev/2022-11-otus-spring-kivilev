package org.kivilev.service;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PollApplicationService {

    private final StudentService studentService;
    private final PollService pollService;

    public void run() {
        val student = studentService.registrationStudent();
        pollService.doPoll(student);
    }
}
