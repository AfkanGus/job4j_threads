package ru.job4j.cash;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * 1. Неблокирующий кеш [#4741]
 */
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        if (memory.containsKey(model.getId())) {
            return false;
        }
        memory.put(model.getId(), model);
        return true;
    }

    public boolean update(Base model) throws OptimisticException {
        /* TODO impl */
        return false;
    }

    public void delete(int id) {
        /* TODO impl */
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
