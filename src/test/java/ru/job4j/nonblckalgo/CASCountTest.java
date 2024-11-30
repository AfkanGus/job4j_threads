package ru.job4j.nonblckalgo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 0. CAS - операции [#6859]
 * Реализовать неблокирующий счетчик
 */
class CASCountTest {

    @Test
    void whenIncrementMultipleThreadsThenCorrectCount() throws InterruptedException {
        CASCount counter = new CASCount();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(counter.get()).isEqualTo(200);
    }
}