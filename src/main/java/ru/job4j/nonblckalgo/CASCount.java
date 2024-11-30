package ru.job4j.nonblckalgo;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 0. CAS - операции [#6859]
 * Реализовать неблокирующий счетчик
 */
@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    /**
     * Увеличивает значение счетчика на 1
     */
    public void increment() {
        int current;
        do {
            current = count.get();
        } while (!count.compareAndSet(current, current + 1));
    }

    /**
     * Возвращает текущее значение счетчика
     *
     * @return текущее значение
     */
    public int get() {
        return count.get();
    }
}
