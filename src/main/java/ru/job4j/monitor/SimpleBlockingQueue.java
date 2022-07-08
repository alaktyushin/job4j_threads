package ru.job4j.monitor;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue;

    public SimpleBlockingQueue() {
        queue = new LinkedList<>();
    }

    public SimpleBlockingQueue(int queueSize) {
        queue = new ArrayBlockingQueue<>(queueSize);
    }

    public void offer(T value) {
        synchronized (this) {
            while (!queue.offer(value)) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.notifyAll();
        }
    }

    public T poll() {
        synchronized (this) {
            T result = queue.poll();
            while (result == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            this.notifyAll();
            return result;
        }
    }
}