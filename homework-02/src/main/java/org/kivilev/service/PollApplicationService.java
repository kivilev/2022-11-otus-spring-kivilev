package org.kivilev.service;

import lombok.val;
import org.springframework.stereotype.Service;

@Service
public class PollApplicationService {

    private final StudentService studentService;
    private final PollService pollService;

    public PollApplicationService(StudentService studentService, PollService pollService) {
        this.studentService = studentService;
        this.pollService = pollService;
    }

    public void run() {
        val student = studentService.registrationStudent();
        pollService.doPoll(student);
    }
}
