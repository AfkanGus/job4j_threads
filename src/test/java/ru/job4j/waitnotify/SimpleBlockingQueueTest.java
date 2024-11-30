package ru.job4j.waitnotify;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * 1. Реализовать шаблон Producer Consumer. [#1098].
 * 3. Junit тест для блокирующей очереди. [#68589].
 * Написать тесты. В тестах должны быть две нити: одна производитель, другая потребитель.
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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> {
                    IntStream.range(0, 5).forEach(
                            i -> {
                                try {
                                    queue.offer(i);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            }
                    );
                }
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted() || !queue.isEmpty()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).containsExactly(0, 1, 2, 3, 4);
    }
}