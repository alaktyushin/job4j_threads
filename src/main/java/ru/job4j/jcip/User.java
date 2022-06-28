package ru.job4j.jcip;

import net.jcip.annotations.ThreadSafe;

import java.util.Objects;

@ThreadSafe
public final class User {

    private final int id;
    private final int amount;

    public User(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized int getAmount() {
        return amount;
    }
}
