package org.kivilev;

import org.kivilev.service.PollApplicationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class HomeworkApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(HomeworkApplication.class);

        var pollApplicationService = context.getBean(PollApplicationService.class);

        pollApplicationService.run();
    }
}
