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
        first.join();
        second.start();
        second.join();
        third.start();
        third.join();
        fourth.start();
        fourth.join();
        StringBuilder expected = new StringBuilder();
        expected
                .append("1=User{id=1, amount=100}")
                .append(System.lineSeparator())
                .append("2=User{id=2, amount=200}")
                .append(System.lineSeparator())
                .append("3=User{id=3, amount=300}")
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
        first.join();
        second.start();
        second.join();
        third.start();
        third.join();
        fourth.start();
        fourth.join();
        fifth.start();
        fifth.join();
        StringBuilder expected = new StringBuilder();
        expected
                .append("1=User{id=1, amount=100}")
                .append(System.lineSeparator())
                .append("2=User{id=2, amount=2222}")
                .append(System.lineSeparator())
                .append("3=User{id=3, amount=3333}")
                .append(System.lineSeparator());
        assertThat(store.printStorage(), is(expected.toString()));
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
        first.join();
        second.start();
        second.join();
        third.start();
        third.join();
        fourth.start();
        fourth.join();
        fifth.start();
        fifth.join();
        StringBuilder expected = new StringBuilder();
        expected
                .append("1=User{id=1, amount=100}")
                .append(System.lineSeparator())
                .append("3=User{id=3, amount=300}")
                .append(System.lineSeparator());
        assertThat(store.printStorage(), is(expected.toString()));
    }

    @Test
    public void whenTransferAmount() throws InterruptedException {
        UserStorage store = new UserStorage();
        Thread first = new Thread(() -> store.add(new User(1, 100)));
        Thread second = new Thread(() -> store.add(new User(2, 200)));
        Thread third = new Thread(() -> store.add(new User(3, 300)));
        Thread fourth = new Thread(() -> store.delete(new User(1, 0)));
        Thread fifth = new Thread(() -> store.transfer(3, 2, 350));
        Thread sixth = new Thread(() -> store.transfer(3, 1, 150));
        Thread seventh = new Thread(() -> store.transfer(4, 2, 150));
        Thread eighth = new Thread(() -> store.transfer(3, 2, 150));
        first.start();
        first.join();
        second.start();
        second.join();
        third.start();
        third.join();
        fourth.start();
        fourth.join();
        fifth.start();
        fifth.join();
        sixth.start();
        sixth.join();
        seventh.start();
        seventh.join();
        eighth.start();
        eighth.join();
        StringBuilder expected = new StringBuilder();
        expected
                .append("2=User{id=2, amount=350}")
                .append(System.lineSeparator())
                .append("3=User{id=3, amount=150}")
                .append(System.lineSeparator());
        assertThat(store.printStorage(), is(expected.toString()));
    }
}
