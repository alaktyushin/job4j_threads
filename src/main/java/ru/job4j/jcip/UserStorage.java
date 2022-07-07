package ru.job4j.jcip;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> userStorage = new ConcurrentHashMap<>();

    synchronized boolean add(final User user) {
        return userStorage.putIfAbsent(user.getId(), user) == null;
    }

    synchronized boolean update(final User user) {
        return userStorage.replace(user.getId(), user) != null;
    }

    synchronized boolean delete(final User user) {
        return userStorage.remove(user.getId()) != null;
    }

    synchronized boolean transfer(final int fromId, final int toId, final int amount) {
        User userFrom = userStorage.get(fromId);
        User userTo = userStorage.get(toId);
        if (fromId == toId
                || userFrom == null
                || userTo == null
                || userFrom.getAmount() < amount) {
            return false;
        }
        update(new User(fromId, userFrom.getAmount() - amount));
        update(new User(toId, userTo.getAmount() + amount));
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
