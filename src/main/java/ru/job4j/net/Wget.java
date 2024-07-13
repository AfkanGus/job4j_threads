package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/**
 * 8. Скачивание файла с ограничением. [#144271]
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
        this.fileName = url.substring(url.lastIndexOf('/') + 1);
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File(fileName);
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: "
                    + (System.currentTimeMillis() - startAt) + " ms");

            byte[] buffer = new byte[1024];
            int bytesRead;
            long startTime = System.currentTimeMillis();
            int totalBytesRead = 0;

            while ((bytesRead = input.read(buffer, 0, buffer.length)) != -1) {
                output.write(buffer, 0, bytesRead);
                /*суммируем общее число скачанных байт*/
                totalBytesRead += bytesRead;
                if (totalBytesRead >= speed) {
                    long timeTaken = System.currentTimeMillis() - startTime;
                    if (timeTaken < 1000) {
                        Thread.sleep(1000 - timeTaken);
                    }
                    totalBytesRead = 0;
                    startTime = System.currentTimeMillis();
                }
            }
            System.out.println("Download complete. File size: "
                    + Files.size(file.toPath()) + " bytes");

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage : java Wget <URL> <speed>");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
