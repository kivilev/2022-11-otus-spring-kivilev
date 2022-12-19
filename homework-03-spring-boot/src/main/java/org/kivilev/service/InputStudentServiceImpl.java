package org.kivilev.service;

import org.kivilev.model.Student;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class InputStudentServiceImpl implements InputStudentService {

    private final Scanner scanner;
    private final PrintStream printStream;
    private final MessageSourceLocale messageSource;

    public InputStudentServiceImpl(InputStream inputStream, PrintStream printStream, MessageSourceLocale messageSource) {
        this.scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        this.printStream = printStream;
        this.messageSource = messageSource;
    }

    @Override
    public Student inputStudent() {
        printStream.print(messageSource.getMessage("enter.user.name", new String[]{}));
        String name = scanner.nextLine();

        printStream.print(messageSource.getMessage("enter.user.age", new String[]{}));
        int age = scanner.nextInt();

        return new Student(name, age);
    }
}
