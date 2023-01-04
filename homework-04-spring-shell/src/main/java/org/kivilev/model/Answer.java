package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Answer {
    private final int position;
    private final String text;

    @Override
    public String toString() {
        return "[" + position + "] '" + text + '\'';
    }
}
