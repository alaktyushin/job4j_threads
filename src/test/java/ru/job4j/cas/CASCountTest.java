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
}