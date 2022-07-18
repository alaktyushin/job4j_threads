package ru.job4j.monitor;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class SimpleBlockingQueueTestWithBuffer {

    @Test
    public void whenAllStartThenAllJoin() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer1 = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ), "Producer-1"
        );
        Thread producer2 = new Thread(
                () -> IntStream.range(5, 10).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ), "Producer-2"
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "Consumer"
        );
        producer1.start();
        producer2.start();
        consumer.start();
        producer1.join();
        producer2.join();
        consumer.interrupt();
        consumer.join();
        assertTrue(buffer.containsAll(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)) && (buffer.size() == 10));
    }

    @Test
    public void whenFetchThenGetThenFetchThenGet() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer1 = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ), "Producer-1"
        );
        Thread producer2 = new Thread(
                () -> IntStream.range(5, 10).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ), "Producer-2"
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "Consumer"
        );
        producer1.start();
        producer1.join();
        consumer.start();
        consumer.interrupt();
        producer2.start();
        producer2.join();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void whenFetchThenFetchThenGet() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);
        Thread producer1 = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ), "Producer-1"
        );
        Thread producer2 = new Thread(
                () -> IntStream.range(5, 10).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                ), "Producer-2"
        );
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }, "Consumer"
        );
        producer1.start();
        producer1.join();
        producer2.start();
        producer2.join();
        consumer.start();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        value -> {
                            try {
                                queue.offer(value);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }
}
