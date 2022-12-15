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

    public InputStudentServiceImpl(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        this.printStream = printStream;
    }

    @Override
    public Student inputStudent() {
        printStream.print("Enter student name: ");
        String name = scanner.nextLine();

        printStream.print("Enter student age: ");
        int age = scanner.nextInt();

        return new Student(name, age);
    }
}
