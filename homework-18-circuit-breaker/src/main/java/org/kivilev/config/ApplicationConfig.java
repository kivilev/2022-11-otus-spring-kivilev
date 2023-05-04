package org.kivilev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ApplicationConfig {
    @Bean
    public Clock mainClock() {
        return Clock.systemUTC();
    }
}
