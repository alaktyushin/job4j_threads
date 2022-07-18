package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    private final AtomicInteger value = new AtomicInteger();

    public CASCount() {
        value.set(0);
    }

    public CASCount(Integer value) {
        this.value.set(value);
    }

    public void increment() {
        Integer temp;
        int newValue;
        do {
            temp = count.get();
            newValue = value.incrementAndGet();
        } while (!count.compareAndSet(temp, newValue));
    }

    public int get() {
        return value.get();
    }
}