package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount() {
        count.set(0);
    }

    public CASCount(Integer value) {
        count.set(value);
    }

    public void increment() {
        Integer temp;
        int newValue;
        do {
            temp = count.get();
            newValue = temp + 1;
        } while (!count.compareAndSet(temp, newValue));
    }

    public int get() {
        return count.get();
    }
}