package ru.job4j.monitor;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();
    private final int queueSize;

    public SimpleBlockingQueue(final int queueSize) {
        this.queueSize = queueSize;
    }

    public void offer(T value) {
        synchronized (this) {
            while (queue.size() >= queueSize) {
                try {
                    System.out.println("Waiting for offer");
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.notifyAll();
            queue.offer(value);
        }
    }

    public T poll() {
        synchronized (this) {
            while (queue.size() == 0) {
                try {
                    System.out.println("Waiting for poll");
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.notifyAll();
            return queue.poll();
        }
    }
}