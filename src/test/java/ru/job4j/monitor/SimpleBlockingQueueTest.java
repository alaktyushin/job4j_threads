package ru.job4j.monitor;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenQueueIsNotBlocked() throws InterruptedException {
        SimpleBlockingQueue queue = new SimpleBlockingQueue(2);
        List list = new LinkedList();
        List expected = List.of(15, 28);
        Thread producerThread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.offer(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " offered");
                },
                "Producer-1"
        );
        Thread producerThread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.offer(28);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " offered");
                },
                "Producer-2"
        );
        Thread consumerThread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    Object polledValue = null;
                    try {
                        polledValue = queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(polledValue);
                    System.out.println(Thread.currentThread().getName() + " polled value: " + polledValue);
                },
                "Consumer-1"
        );
        Thread consumerThread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    Object polledValue = null;
                    try {
                        polledValue = queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(polledValue);
                    System.out.println(Thread.currentThread().getName() + " polled value: " + polledValue);
                },
                "Consumer-2"
        );
        producerThread1.start();
        producerThread1.join();
        consumerThread1.start();
        consumerThread1.join();
        producerThread2.start();
        producerThread2.join();
        consumerThread2.start();
        consumerThread2.join();
        assertThat(list.toString(), is(expected.toString()));
    }

    @Test
    public void whenQueueBlockedByConsumer() throws InterruptedException {
        SimpleBlockingQueue queue = new SimpleBlockingQueue(1);
        List list = new LinkedList();
        List expected = List.of(15, 28);
        Thread producerThread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.offer(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " offered");
                },
                "Producer-1"
        );
        Thread producerThread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.offer(28);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " offered");
                },
                "Producer-2"
        );
        Thread consumerThread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    Object polledValue = null;
                    try {
                        polledValue = queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(polledValue);
                    System.out.println(Thread.currentThread().getName() + " polled value: " + polledValue);
                },
                "Consumer-1"
        );
        Thread consumerThread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    Object polledValue = null;
                    try {
                        polledValue = queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(polledValue);
                    System.out.println(Thread.currentThread().getName() + " polled value: " + polledValue);
                },
                "Consumer-2"
        );
        producerThread1.start();
        producerThread1.join();
        consumerThread1.start();
        consumerThread1.join();
        consumerThread2.start();
        /*
        If uncommented - then the queue is blocked:
        consumerThread2.join();
        */
        producerThread2.start();
        producerThread2.join();
        assertThat(list.toString(), is(expected.toString()));
    }

    @Test
    public void whenRestrictedSizeQueueBlockedByProducer() throws InterruptedException {
        SimpleBlockingQueue queue = new SimpleBlockingQueue(2);
        List list = new LinkedList();
        List expected = List.of(1, 2);
        Thread producerThread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 1; i <= 2; i++) {
                        try {
                            queue.offer(i);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(Thread.currentThread().getName() + " offered");
                    }
                },
                "Producer-1"
        );
        Thread producerThread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.offer(28);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName() + " offered");
                },
                "Producer-2"
        );
        Thread consumerThread1 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    Object polledValue = null;
                    try {
                        polledValue = queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(polledValue);
                    System.out.println(Thread.currentThread().getName() + " polled value: " + polledValue);
                },
                "Consumer-1"
        );
        Thread consumerThread2 = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    Object polledValue = null;
                    try {
                        polledValue = queue.poll();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    list.add(polledValue);
                    System.out.println(Thread.currentThread().getName() + " polled value: " + polledValue);
                },
                "Consumer-2"
        );
        producerThread1.start();
        producerThread1.join();
        producerThread2.start();
        /*
        If uncommented - then the queue is blocked:
        producerThread2.join();
        */
        consumerThread1.start();
        consumerThread1.join();
        consumerThread2.start();
        consumerThread2.join();
        assertThat(list.toString(), is(expected.toString()));
    }
}