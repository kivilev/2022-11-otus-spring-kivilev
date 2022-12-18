package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PollResult {
    private final int correctAnswersCount;
    private final int wrongAnswersCount;
    private final boolean isPassed;
}
