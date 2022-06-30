package ru.job4j.jcip;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class UserStorageTest {

    @Test
    public void whenAddUser() throws InterruptedException {
        UserStorage store = new UserStorage();
        Thread first = new Thread(() -> store.add(new User(1, 100)));
        Thread second = new Thread(() -> store.add(new User(1, 1020)));
        Thread third = new Thread(() -> store.add(new User(2, 200)));
        Thread fourth = new Thread(() -> store.add(new User(3, 300)));
        first.start();
        second.start();
        third.start();
        fourth.start();
        first.join(1000);
        second.join(50);
        third.join(30);
        fourth.join(40);
        StringBuilder expected = new StringBuilder();
        expected
                .append("1=100")
                .append(System.lineSeparator())
                .append("2=200")
                .append(System.lineSeparator())
                .append("3=300")
                .append(System.lineSeparator());
        assertThat(store.printStorage(), is(expected.toString()));
    }

    @Test
    public void whenUpdateUser() throws InterruptedException {
        UserStorage store = new UserStorage();
        Thread first = new Thread(() -> store.add(new User(1, 100)));
        Thread second = new Thread(() -> store.add(new User(2, 200)));
        Thread third = new Thread(() -> store.add(new User(3, 300)));
        Thread fourth = new Thread(() -> store.update(new User(2, 2222)));
        Thread fifth = new Thread(() -> store.update(new User(3, 3333)));
        first.start();
        second.start();
        third.start();
        fourth.start();
        fifth.start();
        first.join(1000);
        second.join(50);
        third.join(30);
        fourth.join(40);
        fifth.join(5);
        System.out.println(store.printStorage());
    }

    @Test
    public void whenDeleteUser() throws InterruptedException {
        UserStorage store = new UserStorage();
        Thread first = new Thread(() -> store.add(new User(1, 100)));
        Thread second = new Thread(() -> store.add(new User(2, 200)));
        Thread third = new Thread(() -> store.add(new User(3, 300)));
        Thread fourth = new Thread(() -> store.delete(new User(2, 2222)));
        Thread fifth = new Thread(() -> store.delete(new User(5, 3333)));
        first.start();
        second.start();
        third.start();
        fourth.start();
        fifth.start();
        first.join(1000);
        second.join(50);
        third.join(30);
        fourth.join(40);
        fifth.join(5);
        System.out.println(store.printStorage());
    }

    @Test
    public void whenTransferAmount() {
        UserStorage store = new UserStorage();
        store.add(new User(1, 100));
        store.add(new User(2, 200));
        store.add(new User(3, 300));
        store.delete(new User(1, 0));
        assertFalse(store.transfer(3, 2, 350));
        assertFalse(store.transfer(3, 1, 150));
        assertFalse(store.transfer(4, 2, 150));
        assertTrue(store.transfer(3, 2, 150));
    }
}