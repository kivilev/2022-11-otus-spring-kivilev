package org.kivilev;

import org.kivilev.service.PollApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeworkApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(HomeworkApplication.class, args);
        context.getBean(PollApplicationService.class).run();
    }
}
