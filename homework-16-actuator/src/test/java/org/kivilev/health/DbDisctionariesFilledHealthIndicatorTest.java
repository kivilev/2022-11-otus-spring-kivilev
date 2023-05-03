/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.health;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kivilev.dao.repository.HealthCheckDao;
import org.mockito.Mockito;
import org.springframework.boot.actuate.health.Health;

import static org.junit.jupiter.api.Assertions.*;

class DbDisctionariesFilledHealthIndicatorTest {
    private final HealthCheckDao healthCheckDao = Mockito.mock(HealthCheckDao.class);
    private final DbDisctionariesFilledHealthIndicator healthIndicator = new DbDisctionariesFilledHealthIndicator(healthCheckDao);

    @Test
    @DisplayName("чекап должен возвращать статус UP, если справочники заполнены")
    public void healthCheckShouldReturnUpStatusForFilledDictionaries() {
        var expectedResult = Health.up().build();
        Mockito.when(healthCheckDao.isDictionariesReady()).thenReturn(true);

        var actualResult = healthIndicator.health();

        assertEquals(expectedResult, actualResult);
    }

    @Test
    @DisplayName("чекап должен возвращать статус Down, если справочники не заполнены")
    public void healthCheckShouldReturnDownStatusForEmptyDictionaries() {
        var expectedResult = Health.down().withDetail("Dictionaries are not filled", "-1").build();
        Mockito.when(healthCheckDao.isDictionariesReady()).thenReturn(false);

        var actualResult = healthIndicator.health();

        assertEquals(expectedResult, actualResult);
    }
}