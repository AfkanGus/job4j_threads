package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

/**
 * 8. Скачивание файла с ограничением. [#144271].
 */
public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        var startAt = System.currentTimeMillis();
        var file = new File("DownLoadingFile.xml");
        try (var input = new URL(url).openStream();
             var output = new FileOutputStream(file)) {
            System.out.println("Open connection: "
                    + (System.currentTimeMillis() - startAt) + " ms");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer, 0, buffer.length)) != -1) {
                long startTime = System.currentTimeMillis();
                output.write(buffer, 0, bytesRead);
                long timeTaken = System.currentTimeMillis() - startTime;
                long expectTime = (long) ((1000.0 / speed) * bytesRead);
                long sleepTime = expectTime - (timeTaken / 1000000);
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
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
