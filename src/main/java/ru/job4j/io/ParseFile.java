package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class ParseFile {

    private final File file;

    ParseFile(final File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
        StringBuilder output = new StringBuilder();
        int data;
        while ((data = bufferedInputStream.read()) > 0) {
            if (filter.test((char) data)) {
                output.append((char) data);
            }
        }
        return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}