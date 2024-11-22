package ru.job4j.waitnotify;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * 1. Реализовать шаблон Producer Consumer. [#1098].
 *  Написать тесты. В тестах должны быть две нити: одна производитель, другая потребитель.
 */
class SimpleBlockingQueueTest {
    @Test
    void whenBlockQueueWithTwoThreads() throws InterruptedException {
        SimpleBlockingQueue simpleBlockingQueue = new SimpleBlockingQueue(4);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            System.out.println("Producing" + i);
                            simpleBlockingQueue.offer(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        Thread consumer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            Integer value = (Integer) simpleBlockingQueue.poll();
                            System.out.println("Consuming" + value);
                            list.add(value);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        List<Integer> expected = List.of(0, 1, 2, 3, 4);
        assertThat(list.size(), is(5));
        assertThat(list, is(expected));
    }
}