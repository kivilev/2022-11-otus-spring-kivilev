package org.kivilev.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class ApplicationConfig {
    private final int correctAnswersMinimumCount;

    public ApplicationConfig(@Value("${correct-answers-minimum-count}") int correctAnswersMinimumCount) {
        this.correctAnswersMinimumCount = correctAnswersMinimumCount;
    }
}