package ru.job4j.jcip;

import java.util.Objects;

public class User {

    private final int id;
    private final int amount;

    public User(final int id, final int amount) {
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

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }
}
