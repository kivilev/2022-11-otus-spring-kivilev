package org.kivilev;

import lombok.val;
import org.kivilev.service.PollService;
import org.kivilev.service.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class HomeworkApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HomeworkApplication.class);

        var studentService = context.getBean(StudentService.class);
        var pollService = context.getBean(PollService.class);

        val student = studentService.registrationStudent();
        pollService.doPoll(student);
    }
}
