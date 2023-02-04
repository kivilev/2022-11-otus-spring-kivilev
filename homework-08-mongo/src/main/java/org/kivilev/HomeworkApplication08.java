package org.kivilev;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
//@EnableMongoRepositories
@SpringBootApplication
public class HomeworkApplication08 {
    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication08.class, args);
    }
}
