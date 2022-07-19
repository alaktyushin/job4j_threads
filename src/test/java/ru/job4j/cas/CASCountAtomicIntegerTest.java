package ru.job4j.cas;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CASCountAtomicIntegerTest {

    @Test
    public void whenGetFromEmpty() {
        CASCountAtomicInteger count = new CASCountAtomicInteger();
        assertEquals(count.get(), 0);
    }

    @Test
    public void whenGetWithDefaultValue() {
        CASCountAtomicInteger count = new CASCountAtomicInteger(2);
        count.increment();
        assertEquals(count.get(), 3);
    }

    @Test
    public void whenGetWithDefaultNegativeValue() {
        CASCountAtomicInteger count = new CASCountAtomicInteger(-2);
        count.increment();
        assertEquals(count.get(), -1);
    }

    @Test
    public void whenIncrement() {
        CASCountAtomicInteger count = new CASCountAtomicInteger();
        List<Integer> list = new ArrayList<>();
        List<Integer> expected = List.of(0, 1, 2, 3);
        list.add(count.get());
        count.increment();
        list.add(count.get());
        count.increment();
        list.add(count.get());
        count.increment();
        list.add(count.get());
        assertEquals(list, expected);
    }

    @Test
    public void whenIncrement300Times() {
        CASCountAtomicInteger count = new CASCountAtomicInteger(-150);
        List<Integer> list = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();
        for (int i = -150; i < 150; i++) {
            expected.add(i);
            list.add(count.get());
            count.increment();
        }
        assertEquals(list, expected);
    }
}