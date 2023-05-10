/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRateInfo {
    private String disclaimer;
    private long timestamp;
    private String base;
    private Map<String, String> rates;

    @JsonCreator
    public CurrencyRateInfo(String disclaimer, long timestamp, String base, Map<String, String> rates) {
        this.disclaimer = disclaimer;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }
}
