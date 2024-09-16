package ru.job4j.io;

import java.io.*;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * 1. Visibility. Общий ресурс вне критической секции [#1102].
 */
public class ParseFile {
    private File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Character> filter) throws IOException {
        StringBuilder output = new StringBuilder();
        int data;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))) {
            while ((data = in.read()) > 0) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String getContentWithoutUnicode() throws IOException {
        return getContent(ch -> ch < 0x80);
    }

    public synchronized String getContent() throws IOException {
        return getContent(ch -> true);
    }
}

