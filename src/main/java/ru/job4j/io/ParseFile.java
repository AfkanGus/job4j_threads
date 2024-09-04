package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

/**
 * 1. Visibility. Общий ресурс вне критической секции [#1102].
 */
public final class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder out = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new
                InputStreamReader(new FileInputStream(file)))) {
            int data;
            while ((data = reader.read()) != 1) {
                char character = (char) data;
                if (filter.test(character)) {
                    out.append(character);
                }
            }
        }
        return out.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent((ch -> ch < 0x80));
    }
}

