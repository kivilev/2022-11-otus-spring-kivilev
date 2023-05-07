/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.data.mongodb")
@ConstructorBinding
@Getter
@RequiredArgsConstructor
public class MongoDbConfigurationProperties {
    private final String uri;
    private final String database;
}
