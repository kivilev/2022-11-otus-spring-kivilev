package org.kivilev.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.kivilev.config.CsvConfig;
import org.kivilev.dao.dto.QuestionAnswersDto;
import org.kivilev.dao.mapper.CsvMapper;
import org.kivilev.model.Question;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionDaoCsvImpl implements QuestionDao {

    private final String csvFileName;
    private final CsvMapper csvMapper;

    public QuestionDaoCsvImpl(CsvMapper csvMapper, CsvConfig csvConfig) {
        this.csvMapper = csvMapper;
        this.csvFileName = csvConfig.getCsvFileName();
    }

    @SneakyThrows
    @Override
    public List<Question> getQuestions() {
        var lines = readCsvFile();
        return lines.stream().map(csvMapper::fromDto).collect(Collectors.toList());
    }

    public List<QuestionAnswersDto> readCsvFile() throws Exception {
        try (InputStream inputStream = QuestionDaoCsvImpl.class.getResourceAsStream("/" + this.csvFileName)) {
            assert inputStream != null;
            try (Reader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {

                CsvToBean<QuestionAnswersDto> cb = new CsvToBeanBuilder<QuestionAnswersDto>(streamReader)
                        .withQuoteChar('\"')
                        .withSeparator(';')
                        .withType(QuestionAnswersDto.class)
                        .build();
                return cb.parse();
            }
        }
    }
}
