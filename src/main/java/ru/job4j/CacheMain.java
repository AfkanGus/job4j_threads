package ru.job4j;

/**
 * 0. Что такое атомарность? [#6856].
 *
 */
public class CacheMain {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            Cache instance = Cache.getInstance();
            System.out.println(instance);
        };
        Thread first = new Thread(task);
        Thread second = new Thread(task);
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
