package org.kivilev.service.io;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
public class IoStreamServiceImpl implements IoStreamService {

    private final Scanner scanner;
    private final PrintStream outputStream;

    public IoStreamServiceImpl() {
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.outputStream = System.out;
    }

    @Override
    public String inputString() {
        return scanner.nextLine();
    }

    @Override
    public int inputInt() {
        return Integer.parseInt(scanner.nextLine());
    }

    @Override
    public String inputStringWithLabel(String label) {
        print(label);
        return inputString();
    }

    @Override
    public int inputIntWithLabel(String label) {
        print(label);
        return inputInt();
    }

    @Override
    public long inputLong() {
        return Long.parseLong(scanner.nextLine());
    }

    @Override
    public long inputLongWithLabel(String label) {
        print(label);
        return inputLong();
    }

    @Override
    public void println(String string) {
        outputStream.println(string);
    }

    @Override
    public void print(String message) {
        outputStream.print(message);
    }

    @Override
    public void format(String format, Object... args) {
        outputStream.format(format, args);
    }
}
