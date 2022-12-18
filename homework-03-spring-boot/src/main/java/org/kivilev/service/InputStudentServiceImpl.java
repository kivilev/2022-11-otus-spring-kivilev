package org.kivilev.service;

import org.kivilev.config.PollsProperties;
import org.kivilev.model.Student;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class InputStudentServiceImpl implements InputStudentService {

    private final Scanner scanner;
    private final PrintStream printStream;
    private final MessageSource messageSource;
    private final PollsProperties pollsProperties;

    public InputStudentServiceImpl(InputStream inputStream, PrintStream printStream, MessageSource messageSource, PollsProperties pollsProperties) {
        this.scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        this.printStream = printStream;
        this.messageSource = messageSource;
        this.pollsProperties = pollsProperties;
    }

    @Override
    public Student inputStudent() {
        printStream.print(messageSource.getMessage("enter.user.name", new String[]{}, pollsProperties.getLocale()));
        String name = scanner.nextLine();

        printStream.print(messageSource.getMessage("enter.user.age", new String[]{}, pollsProperties.getLocale()));
        int age = scanner.nextInt();

        return new Student(name, age);
    }
}
