package ru.job4j.io;

import java.io.*;

/**
 * 1. Visibility. Общий ресурс вне критической секции [#1102].
 */
public final class SavePFile {
    private final File file;

    public SavePFile(File file) {
        this.file = file;
    }

    public synchronized void saveContent(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new
                OutputStreamWriter(new FileOutputStream(file)))) {
            for (int i = 0; i < content.length(); i++) {
                writer.write(content);
            }
        }
    }
}
