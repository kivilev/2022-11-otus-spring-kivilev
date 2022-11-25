package org.kivilev;

import org.kivilev.service.PollService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HomeworkApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        var pollService = context.getBean(PollService.class);
        pollService.printQuestionsAndAnswers();
    }
}
