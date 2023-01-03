package org.kivilev.config;

import lombok.Getter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@EnableConfigurationProperties({PollsProperties.class, QuestionSourceConfig.class})
public class ApplicationConfig {

}