package ru.job4j.buffer;

import ru.job4j.monitor.SimpleBlockingQueue;

public class ParallelSearch {

    public static void main(String[] args) {
        final int QUEUE_SIZE = 3;
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(QUEUE_SIZE);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            System.out.println("Consumer stopped by exception: " + e);
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    for (int index = 0; index != QUEUE_SIZE; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    consumer.interrupt();
                }
        ).start();
    }
}
