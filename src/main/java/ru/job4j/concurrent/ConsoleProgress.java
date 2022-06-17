package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {
        char[] process = {'\\', '|', '/', '-' };
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            i = (i == (process.length - 1) ? 0 : i + 1);
            System.out.print("\rLoading..." + process[i]);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("\rLoaded.");
                break;
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        progress.interrupt();
    }
}
