/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "library")
@ConstructorBinding
@Getter
public class LibraryProperties {
    private final int countTopComments;

    public LibraryProperties(int countTopComments) {
        this.countTopComments = countTopComments;
    }
}
