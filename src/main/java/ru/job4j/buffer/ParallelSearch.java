package ru.job4j.buffer;


import ru.job4j.waitnotify.SimpleBlockingQueue;

/**
 * 2. Обеспечить остановку потребителя. [#66825].
 * В этом задании нужно разработать механизм остановки потребителя,
 * когда производитель закончил свою работу.
 */
public class ParallelSearch {
    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(10);
        final Thread consumer = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()
                    ) {
                        try {
                            System.out.println(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        Thread producer = new Thread(
                () -> {
                    for (int index = 0; index != 3; index++) {
                        try {
                            queue.offer(index);
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        producer.start();
        /*ожидания завершения работы producer*/
        producer.join();
        /*корректно завершить consumer*/
        consumer.interrupt();
    }
}
