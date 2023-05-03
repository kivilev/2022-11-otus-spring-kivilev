/*
 * Copyright (c) 2023.
 * Author: Kivilev Denis <kivilev.d@gmail.com>
 */

package org.kivilev.health;

import org.kivilev.dao.repository.HealthCheckDao;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class DbDisctionariesFilledHealthIndicator implements HealthIndicator {

    private final HealthCheckDao healthCheckDao;

    public DbDisctionariesFilledHealthIndicator(HealthCheckDao healthCheckDao) {
        this.healthCheckDao = healthCheckDao;
    }

    @Override
    public Health health() {
        if (!healthCheckDao.isDictionariesReady()) {
            return Health.down().withDetail("Dictionaries are not filled", "-1").build();
        }
        return Health.up().build();
    }
}