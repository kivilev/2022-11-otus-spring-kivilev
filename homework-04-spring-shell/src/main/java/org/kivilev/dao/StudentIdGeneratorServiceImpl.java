package org.kivilev.dao;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class StudentIdGeneratorServiceImpl implements StudentIdGeneratorService {
    private final AtomicLong currentId = new AtomicLong(0);

    @Override
    public Long getNewId() {
        return currentId.incrementAndGet();
    }
}
