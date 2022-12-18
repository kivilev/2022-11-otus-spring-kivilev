package org.kivilev.config;

import lombok.Getter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@Getter
@EnableConfigurationProperties({PollsProperties.class, QuestionSourceConfig.class})
public class ApplicationConfig {

    @Bean
    public InputStream inputStream() {
        return System.in;
    }

    @Bean
    public PrintStream printStream() {
        return System.out;
    }
}