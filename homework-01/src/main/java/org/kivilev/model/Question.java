package org.kivilev.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class Question {
    private final String questionText;
    private final List<Answer> answers;
    private final List<Integer> rightAnswersPositions;

    public Optional<Answer> getAnswer(int position) {
        return answers
                .stream()
                .filter(answer -> answer.getPosition() == position)
                .findFirst();
    }
}
