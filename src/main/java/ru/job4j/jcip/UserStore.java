package ru.job4j.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, Integer> userStore = new ConcurrentHashMap<>();

    private synchronized boolean manageUser(final User user, final boolean pred1, final boolean pred2) {
        if (pred1) {
            return false;
        }
        if (pred2) {
            userStore.put(user.getId(), user.getAmount());
        } else {
            userStore.remove(user.getId());
        }
        return true;
    }

    synchronized boolean add(final User user) {
        return manageUser(user, userStore.containsKey(user.getId()), true);
    }

    synchronized boolean update(final User user) {
        return manageUser(user, !userStore.containsKey(user.getId()), true);
    }

    synchronized boolean delete(final User user) {
        return manageUser(user, !userStore.containsKey(user.getId()), false);
    }

    synchronized boolean transfer(final int fromId, final int toId, final int amount) {
        if (!userStore.containsKey(fromId)
                || !userStore.containsKey(toId)
                || (userStore.get(fromId) < amount)) {
            return false;
        }
        userStore.put(fromId, userStore.get(fromId) - amount);
        userStore.put(toId, userStore.get(toId) + amount);
        return true;
    }
}

