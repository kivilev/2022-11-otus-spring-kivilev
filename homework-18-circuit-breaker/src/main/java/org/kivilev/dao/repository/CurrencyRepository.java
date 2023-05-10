/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.dao.repository;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Getter
public class CurrencyRepository {
    private final Map<String, String> currencyRates = Map.of("USD", "0.0126092", "EUR", "0.01141878");
}
