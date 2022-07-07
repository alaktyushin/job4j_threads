package ru.job4j.synch;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        List<Integer> start = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(start);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 2)));
    }

    @Test
    public void addThirdThenSecond() throws InterruptedException {
        List<Integer> start = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(start);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        Thread third = new Thread(() -> list.add(3));
        first.start();
        second.start();
        third.start();
        first.join();
        third.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl, is(Set.of(1, 3, 2)));
    }

    @Test
    public void addNothing() {
        List<Integer> start = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(start);
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertEquals(rsl, Set.of());
    }

    @Test
    public void getThird() throws InterruptedException {
        List<Integer> start = new ArrayList<>();
        SingleLockList<Integer> list = new SingleLockList<>(start);
        Thread first = new Thread(() -> list.add(1));
        Thread second = new Thread(() -> list.add(2));
        Thread third = new Thread(() -> list.add(3));
        final int[] x = {0};
        Thread get = new Thread(() -> x[0] = list.get(2));
        first.start();
        second.start();
        third.start();
        get.start();
        first.join();
        second.join();
        third.join();
        get.join();
        assertThat(x[0], is(3));
    }
}