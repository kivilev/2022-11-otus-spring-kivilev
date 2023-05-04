/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.config.CurrencyConfig;
import org.kivilev.dao.repository.CurrencyRepository;
import org.kivilev.service.model.CurrencyRateInfo;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestOperations;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CurrencyRateServiceTest {

    @Autowired
    private CurrencyRateService currencyRateService;
    @MockBean
    private CurrencyConfig currencyConfig;
    @MockBean
    private CurrencyRepository currencyRepository;
    @MockBean
    private RestOperations restTemplate;

    @Test
    @DisplayName("В случае ошибки должны вернуться результаты из подстраховочного метода")
    public void fallbackCurrencyRatesShouldBeReturnedIfErrorHappens() {
        var wrongUrl = "wrong url";
        var fallbackCurrencyRates = Map.of("AAA", "1.1", "CCC", "1.222");

        Mockito.when(restTemplate.getForEntity(wrongUrl, CurrencyRateInfo.class)).thenThrow(RuntimeException.class);
        Mockito.when(currencyRepository.getCurrencyRates()).thenReturn(fallbackCurrencyRates);
        Mockito.when(currencyConfig.getUrl()).thenReturn(wrongUrl);

        var actualResult = currencyRateService.getActualCurrencies();

        assertEquals(fallbackCurrencyRates, actualResult);
    }
}