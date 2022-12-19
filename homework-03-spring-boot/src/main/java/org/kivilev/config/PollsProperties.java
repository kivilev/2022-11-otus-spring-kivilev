package org.kivilev.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Locale;

@ConfigurationProperties(prefix = "polls")
@ConstructorBinding
@Getter
public class PollsProperties {
    private final int correctAnswersMinimumCount;
    private final Locale locale;

    public PollsProperties(int correctAnswersMinimumCount, Locale locale) {
        this.correctAnswersMinimumCount = correctAnswersMinimumCount;
        this.locale = locale;
    }
}
