package ru.job4j.thread;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class Wget implements Runnable {
    private final String url;
    private final long speed;

    public Wget(final String url, final long speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream =
                     new FileOutputStream("./data/" + Paths.get(url).getFileName())) {
            byte[] dataBuffer = new byte[512];
            int bytesRead;
            int bytesDownloaded = 0;
            long downloadSum = 0;
            Instant start = Instant.now();
            Instant downloadStart = Instant.now();
            System.out.println("Download started: " + start);
            while ((bytesRead = in.read(dataBuffer, 0, 512)) != -1) {
                bytesDownloaded += bytesRead;
                downloadSum += bytesRead;
                if (bytesDownloaded >= speed) {
                    long duration = Duration.between(start, Instant.now()).toMillis();
                    if (duration < 1000) {
                        Thread.sleep(1000 - duration);
                    }
                    bytesDownloaded = 0;
                    start = Instant.now();
                }
                System.out.print("\rBytes downloaded: " + downloadSum);
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("\rDownload finished: " + Instant.now());
            System.out.println("Duration in seconds: " + Duration.between(downloadStart, Instant.now()).getSeconds());
            System.out.println("Total bytes downloaded: " + downloadSum);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArgsName argsName = ArgsName.of(args);
        String url = argsName.get("url");
        int speed = Integer.parseInt(argsName.get("speed"));
        System.out.println("Given URL: " + url);
        System.out.println("Given speed: " + speed + " B/s");
        System.out.println("File will be saved as: ./data/" + Paths.get(url).getFileName());
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
