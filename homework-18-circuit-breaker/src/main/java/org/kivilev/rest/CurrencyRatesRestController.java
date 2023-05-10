/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.rest;

import lombok.RequiredArgsConstructor;
import org.kivilev.controller.dto.CurrencyRateResponseDto;
import org.kivilev.controller.mapper.CurrencyRateDtoMapper;
import org.kivilev.service.CurrencyRateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CurrencyRatesRestController {
    private final CurrencyRateService currencyRateService;
    private final CurrencyRateDtoMapper mapper;

    @GetMapping("/api/v1/currency-rates")
    public List<CurrencyRateResponseDto> listCurrencyRates() {
        return mapper.toDto(currencyRateService.getActualCurrencies());
    }
}
