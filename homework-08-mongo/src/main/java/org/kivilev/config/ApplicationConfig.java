package org.kivilev.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.Clock;

@Configuration
@EnableMongoRepositories(basePackages = "org.kivilev.dao.repository")
@EnableConfigurationProperties(LibraryProperties.class)
public class ApplicationConfig {
    @Bean
    public Clock mainClock() {
        return Clock.systemUTC();
    }
}
