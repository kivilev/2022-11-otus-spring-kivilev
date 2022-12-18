package org.kivilev.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "question-source")
@ConstructorBinding
@Getter
public class QuestionSourceConfig {
    private final String csvFileName;

    public QuestionSourceConfig(String csvFileName) {
        this.csvFileName = csvFileName;
    }
}
