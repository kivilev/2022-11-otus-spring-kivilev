package org.kivilev.service;

import lombok.AllArgsConstructor;
import org.kivilev.model.Student;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InputStudentServiceImpl implements InputStudentService {

    private final IoStreamService ioStreamService;
    private final MessageSourceLocale messageSource;

    @Override
    public Student inputStudent() {
        ioStreamService.print(messageSource.getMessage("enter.user.name", new String[]{}));
        String name = ioStreamService.inputString();
        if (name == null) {
            throw new IllegalArgumentException("Invalid name");
        }

        ioStreamService.print(messageSource.getMessage("enter.user.age", new String[]{}));
        int age = ioStreamService.inputInt();
        if (age < 18 || age >= 100) {
            throw new IllegalArgumentException("Invalid age");
        }

        return new Student(name, age);
    }
}
