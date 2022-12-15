package org.kivilev.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class ApplicationConfig {
    private final int correctAnswersMinimumCount;

    public ApplicationConfig(@Value("${correct-answers-minimum-count}") int correctAnswersMinimumCount) {
        this.correctAnswersMinimumCount = correctAnswersMinimumCount;
    }

    @Bean
    public InputStream inputStream() {
        return System.in;
    }

    @Bean
    public PrintStream printStream() {
        return System.out;
    }
}