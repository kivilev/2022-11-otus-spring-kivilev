package org.kivilev.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Student {
    private long id;
    private final String name;
    private final int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setId(long id) {
        this.id = id;
    }
}