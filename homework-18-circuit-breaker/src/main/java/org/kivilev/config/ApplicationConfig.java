package org.kivilev.config;

import com.google.common.cache.CacheBuilder;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
@EnableConfigurationProperties(CurrencyConfig.class)
@EnableCaching
public class ApplicationConfig {
    @Bean
    public Clock mainClock() {
        return Clock.systemUTC();
    }

    @Bean
    @SuppressFBWarnings
    public CacheManager currencyRateCacheManager(CurrencyConfig currencyConfig) {
        return new ConcurrentMapCacheManager() {
            @Override
            protected ConcurrentMapCache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(currencyConfig.getTtl())
                                .build().asMap(),
                        false);
            }
        };
    }

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }
}
