/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConfigurationProperties(prefix = "currency-rates")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
public class CurrencyConfig {
    private final String url;
    private final Duration ttl;
}
