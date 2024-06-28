package ru.job4j.concurrent;

/**
 * 3. Состояние нити. [#229175].
 *
 */
public class ThreadStateTwo {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        Thread second = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName());
                }
        );
        System.out.println("first before start:" + first.getState());
        System.out.println("second before start:" + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED
                || second.getState() != Thread.State.TERMINATED) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println("first final state: " + first.getState());
        System.out.println("second final state: " + second.getState());
    }
}
