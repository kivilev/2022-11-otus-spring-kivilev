package org.kivilev.service;

import org.kivilev.model.Student;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Component
public class InputStudentServiceImpl implements InputStudentService {
    @Override
    public Student inputStudent() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student age: ");
        int age = scanner.nextInt();
        return new Student(name, age);
    }
}
