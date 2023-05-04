/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.controller.mapper;

import org.kivilev.controller.dto.CurrencyRateResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CurrencyRateDtoMapper {

    public List<CurrencyRateResponseDto> toDto(Map<String, String> currencyRates) {
        return currencyRates.entrySet().stream()
                .map(entry -> new CurrencyRateResponseDto(entry.getKey(), Double.parseDouble(entry.getValue())))
                .collect(Collectors.toList());
    }
}
