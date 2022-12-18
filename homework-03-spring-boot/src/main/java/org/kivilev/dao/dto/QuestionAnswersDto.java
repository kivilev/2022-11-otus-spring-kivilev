package org.kivilev.dao.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswersDto {
    @CsvBindByName(column = "Question")
    private String question;

    @CsvBindByName(column = "RightAnswerPositions")
    private String rightAnswerPositions;

    @CsvBindByName(column = "Answer1")
    private String answer1;

    @CsvBindByName(column = "Answer2")
    private String answer2;

    @CsvBindByName(column = "Answer3")
    private String answer3;

    @CsvBindByName(column = "Answer4")
    private String answer4;

    @CsvBindByName(column = "Answer5")
    private String answer5;

    @CsvBindByName(column = "Answer6")
    private String answer6;
}
