package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCountAtomicInteger {

    private final AtomicInteger count = new AtomicInteger();

    public CASCountAtomicInteger() {
        count.set(0);
    }

    public CASCountAtomicInteger(Integer value) {
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