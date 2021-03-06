package ru.job4j.cas;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenGetFromEmpty() {
        CASCount count = new CASCount();
        assertEquals(count.get(), 0);
    }

    @Test
    public void whenGetWithDefaultValue() {
        CASCount count = new CASCount(2);
        count.increment();
        assertEquals(count.get(), 3);
    }

    @Test
    public void whenGetWithDefaultNegativeValue() {
        CASCount count = new CASCount(-2);
        count.increment();
        assertEquals(count.get(), -1);
    }

    @Test
    public void whenIncrement() {
        CASCount count = new CASCount();
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
        CASCount count = new CASCount(-150);
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
