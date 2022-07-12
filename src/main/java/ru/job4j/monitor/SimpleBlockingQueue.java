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

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= queueSize) {
                System.out.println("Waiting for offer");
                this.wait();
            }
            queue.offer(value);
            this.notifyAll();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            while (queue.isEmpty()) {
                System.out.println("Waiting for poll");
                this.wait();
            }
            T polledValue = queue.poll();
            this.notifyAll();
            return polledValue;
        }
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }
}