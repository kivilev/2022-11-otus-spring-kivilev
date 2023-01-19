package org.kivilev.service.io;

public interface InputDataStream {
    String inputString();

    int inputInt();

    String inputStringWithLabel(String label);

    int inputIntWithLabel(String label);

    long inputLong();

    long inputLongWithLabel(String label);
}
