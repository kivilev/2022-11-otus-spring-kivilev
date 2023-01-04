package org.kivilev.dao.mapper;

import org.kivilev.dao.dto.QuestionAnswersDto;
import org.kivilev.model.Answer;
import org.kivilev.model.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvMapper {

    public Question fromDto(QuestionAnswersDto questionAnswersDto) {
        return new Question(questionAnswersDto.getQuestion(),
                mapAnswersFromDto(questionAnswersDto),
                Arrays.stream(questionAnswersDto.getRightAnswerPositions().split(",")).map(Integer::valueOf).collect(Collectors.toList())
        );
    }

    private List<Answer> mapAnswersFromDto(QuestionAnswersDto questionAnswersDto) {
        List<Answer> list = new ArrayList<>();
        if (!questionAnswersDto.getAnswer1().isBlank()) {
            list.add(mapAnswerFromDto(0, questionAnswersDto.getAnswer1()));
        }
        if (!questionAnswersDto.getAnswer2().isBlank()) {
            list.add(mapAnswerFromDto(1, questionAnswersDto.getAnswer2()));
        }
        if (!questionAnswersDto.getAnswer3().isBlank()) {
            list.add(mapAnswerFromDto(2, questionAnswersDto.getAnswer3()));
        }
        if (!questionAnswersDto.getAnswer4().isBlank()) {
            list.add(mapAnswerFromDto(3, questionAnswersDto.getAnswer4()));
        }
        if (!questionAnswersDto.getAnswer5().isBlank()) {
            list.add(mapAnswerFromDto(4, questionAnswersDto.getAnswer5()));
        }
        if (!questionAnswersDto.getAnswer6().isBlank()) {
            list.add(mapAnswerFromDto(5, questionAnswersDto.getAnswer6()));
        }
        return list;
    }

    private Answer mapAnswerFromDto(int position, String text) {
        return new Answer(position, text);
    }
}