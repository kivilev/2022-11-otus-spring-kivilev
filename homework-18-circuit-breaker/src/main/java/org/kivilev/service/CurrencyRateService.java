/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kivilev.config.CurrencyConfig;
import org.kivilev.dao.repository.CurrencyRepository;
import org.kivilev.service.model.CurrencyRateInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyRateService {
    private final CurrencyConfig currencyConfig;
    private final ObjectMapper objectMapper;
    private final RestOperations restTemplate;

    private final CurrencyRepository currencyRepository;

    @Cacheable(value = "currencyRates", cacheManager = "currencyRateCacheManager")
    @CircuitBreaker(name = "currencyRates", fallbackMethod = "getActualCurrenciesFallback")
    public Map<String, String> getActualCurrencies() {
        var answer = restTemplate.getForEntity(currencyConfig.getUrl(), String.class);
        try {
            CurrencyRateInfo currencyRateInfo = objectMapper.readValue(answer.getBody(), CurrencyRateInfo.class);
            return currencyRateInfo.getRates();
        } catch (JsonProcessingException e) {
            var errorMessage = String.format("Проблемы с получением курсов валют. %s", e);
            log.error(errorMessage);
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getActualCurrenciesFallback(Exception e) {
        log.warn("Использован fallback метод для получения данных по курсам из БД");
        log.warn(e.toString());
        // здесь могла быть загрузка из БД, но делать сохранение и загрузку уж совсем не хотелось
        return currencyRepository.getCurrencyRates();
    }
}
