package ru.job4j.ref;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 4. Thread без общих ресурсов [#267919]
 */
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), user);
    }

    public User findById(int id) {
        return users.get(id);
    }
}
