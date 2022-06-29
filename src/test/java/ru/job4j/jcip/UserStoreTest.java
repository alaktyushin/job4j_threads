package ru.job4j.jcip;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserStoreTest {

    @Test
    public void whenAddUser() {
        UserStore store = new UserStore();
        assertTrue(store.add(new User(1, 100)));
        assertFalse(store.add(new User(1, 1000)));
        assertTrue(store.add(new User(2, 200)));
        assertTrue(store.add(new User(3, 300)));
    }

    @Test
    public void whenUpdateUser() {
        UserStore store = new UserStore();
        store.add(new User(1, 100));
        store.add(new User(2, 200));
        store.add(new User(3, 300));
        assertFalse(store.update(new User(5, 1000)));
        assertTrue(store.update(new User(1, 1000)));
    }

    @Test
    public void whenDeleteUser() {
        UserStore store = new UserStore();
        store.add(new User(1, 100));
        store.add(new User(2, 200));
        store.add(new User(3, 300));
        assertFalse(store.delete(new User(5, 1000)));
        assertTrue(store.delete(new User(1, 1000)));
        assertFalse(store.delete(new User(1, 100)));
    }

    @Test
    public void whenTransferAmount() {
        UserStore store = new UserStore();
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