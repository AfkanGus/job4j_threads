package ru.job4j.synchronizers;

import java.util.concurrent.CountDownLatch;

/**
 * 6. Синхронизаторы [#504906].
 */
public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        /*Создаем CountDownLatch с числом 3 — это означает, что основной поток*/
        /*будет ожидать завершения 3 других потоков.*/
        CountDownLatch latch = new CountDownLatch(3);

        /*Создаем три рабочих потока, каждый из которых уменьшит счетчик CountDownLatch на 1.*/
        Runnable worker1 = () -> {
            try {
                /* Симулируем выполнение задачи*/
                System.out.println("Рабочий поток 1 выполняет задачу...");
                Thread.sleep(2000); /*Задержка в 2 секунды для имитации работы*/
                System.out.println("Рабочий поток 1 завершил задачу.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                /*Уменьшаем счетчик на 1*/
                latch.countDown();
            }
        };

        Runnable worker2 = () -> {
            try {
                System.out.println("Рабочий поток 2 выполняет задачу...");
                Thread.sleep(3000); /*Задержка в 3 секунды*/
                System.out.println("Рабочий поток 2 завершил задачу.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        };

        Runnable worker3 = () -> {
            try {
                System.out.println("Рабочий поток 3 выполняет задачу...");
                Thread.sleep(1000); /*Задержка в 1 секунду*/
                System.out.println("Рабочий поток 3 завершил задачу.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        };

        /*Запускаем все три рабочих потока*/
        new Thread(worker1).start();
        new Thread(worker2).start();
        new Thread(worker3).start();

        /*Основной поток будет ожидать, пока счетчик latch не станет равен 0*/
        System.out.println("Основной поток ждет завершения задач рабочих потоков...");
        latch.await(); /*Метод блокирует основной поток, пока счетчик не станет равен 0*/
        System.out.println("Все рабочие потоки завершили задачи. "
                + "Основной поток продолжает работу.");
    }
    /**
     *  Что здесь происходит:
     CountDownLatch latch = new CountDownLatch(3); — создается объект CountDownLatch с
     начальным значением счетчика 3. Это означает, что основной поток будет ожидать,
     пока 3 других потока завершат свою работу.
     Три рабочих потока (worker1, worker2, worker3) выполняют свои задачи и в
     конце каждой задачи вызывают latch.countDown();, уменьшая счетчик на 1.
     Основной поток вызывает latch.await();, что заставляет его ждать, пока
     счетчик не достигнет 0.
     После завершения всех рабочих потоков (счетчик станет равен 0), основной
     поток "разблокируется" и продолжает свою работу.
     */
}
