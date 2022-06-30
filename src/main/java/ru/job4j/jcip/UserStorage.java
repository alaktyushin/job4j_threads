package ru.job4j.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, Integer> userStorage = new ConcurrentHashMap<>();

    boolean add(final User user) {
        return userStorage.putIfAbsent(user.getId(), user.getAmount()) == null;
    }

    synchronized boolean update(final User user) {
        return userStorage.replace(user.getId(), user.getAmount()) != null;
    }

    synchronized boolean delete(final User user) {
        return userStorage.remove(user.getId()) != null;
    }

    synchronized boolean transfer(final int fromId, final int toId, final int amount) {
        if (fromId == toId
                || (userStorage.get(toId) == null)
                || (userStorage.get(fromId) == null)
                || (userStorage.get(fromId) < amount)) {
            return false;
        }
        userStorage.replace(fromId, userStorage.get(fromId) - amount);
        userStorage.replace(toId, userStorage.get(toId) + amount);
        return true;
    }

    synchronized String printStorage() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry i : userStorage.entrySet()) {
            result.append(i).append(System.lineSeparator());
        }
        return result.toString();
    }
}
