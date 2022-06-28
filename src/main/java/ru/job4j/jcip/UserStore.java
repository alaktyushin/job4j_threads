package ru.job4j.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;

@ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final HashSet<User> userStore = new HashSet<>();

    private synchronized boolean add(User user) {
        return userStore.add(user);
    }

    private synchronized boolean update(User user) {
        boolean result = false;
        if (userStore.contains(user)) {
            userStore.remove(user);
            userStore.add(user);
            result = true;
        }
        return result;
    }

    private synchronized boolean delete(User user) {
        return userStore.remove(user);
    }

    private synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        boolean res1 = false;
        boolean res2 = false;
        User u1 = null;
        User u2 = null;
        for (var i : userStore) {
            if (i.getId() == fromId) {
                u1 = new User(fromId, i.getAmount() - amount);
                res1 = true;
            }
            if (i.getId() == toId) {
                u2 = new User(toId, i.getAmount() + amount);
                res2 = true;
            }
            result = res1 && res2;
        }
        if (result) {
            userStore.remove(u1);
            userStore.add(u1);
            userStore.remove(u2);
            userStore.add(u2);
        }
        return result;
    }
}
