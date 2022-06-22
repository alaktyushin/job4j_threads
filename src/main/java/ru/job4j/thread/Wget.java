package ru.job4j.thread;

import java.io.*;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        double estimatedSpeed = getSpeed(url);
        long timeout = (estimatedSpeed < speed) ? 0 : Math.round(estimatedSpeed / speed * 1000);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                System.out.println("Timeout, millis: " + timeout);
                Thread.sleep(timeout);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private double getSpeed(String url) {
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(new URL(url).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        int blocksCount = 0;
        int bytesCount = 0;
        while (true) {
            try {
                bytesRead = in.read(dataBuffer, 0, 1024);
                if (!(bytesRead != -1 && blocksCount < 100)) {
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            blocksCount++;
            bytesCount += bytesRead;
        }
        System.out.println("Estimated speed: " + bytesCount / blocksCount + " B/s");
        return bytesCount / blocksCount;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        System.out.println("Given URL: " + url);
        System.out.println("Given speed: " + speed + " B/s");
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}