package org.kivilev.service.io;

public interface OutputStreamData {
    void println(String string);

    void print(String message);

    void format(String format, Object... args);
}
