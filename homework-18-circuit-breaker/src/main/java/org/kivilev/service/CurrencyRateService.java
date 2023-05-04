/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.kivilev.config.CurrencyConfig;
import org.kivilev.service.model.CurrencyRateInfo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyRateService {
    private final CurrencyConfig currencyConfig;
    private final ObjectMapper objectMapper;
    private final RestOperations restTemplate;

    @Cacheable(value = "currency-rates", cacheManager = "currencyRateCacheManager")
    public Map<String, String> getActualCurrencies() {
        var answer = restTemplate.getForEntity(currencyConfig.getUrl(), String.class);
        try {
            CurrencyRateInfo currencyRateInfo = objectMapper.readValue(answer.getBody(), CurrencyRateInfo.class);
            return currencyRateInfo.getRates();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(String.format("Проблемы с получением курсов валют. %s", e));
        }
    }
}
