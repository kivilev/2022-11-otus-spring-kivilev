package org.kivilev.service;

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
    public void println(String string) {
        outputStream.println(string);
    }

    @Override
    public void print(String message) {
        outputStream.print(message);
    }
}
