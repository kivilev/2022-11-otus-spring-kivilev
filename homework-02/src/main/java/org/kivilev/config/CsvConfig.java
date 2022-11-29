package org.kivilev.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@Getter
public class CsvConfig {

    private final String csvFileName;

    public CsvConfig(@Value("${csv-file-name}") String csvFileName) {
        this.csvFileName = csvFileName;
    }
}
