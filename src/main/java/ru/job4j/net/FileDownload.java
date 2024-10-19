package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/**
 * 8. Скачивание файла с ограничением. [#144271 #504769].
 * Программа должна скачивать файл из сети с ограничением по скорости скачки.
 */
public class FileDownload {
    public static void main(String[] args) throws IOException {
        var startAt = System.currentTimeMillis();
        var file = new File("tmp.xml");
        String url = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: "
                    + (System.currentTimeMillis() - startAt) + " ms");
            var dataBuffer = new byte[512];
            int bytesRead;
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var downloadAt = System.nanoTime();
                output.write(dataBuffer, 0, bytesRead);
                System.out.println("Read 512 bytes : "
                        + (System.nanoTime() - downloadAt) + " nano.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(Files.size(file.toPath()) + " bytes");
    }
}
