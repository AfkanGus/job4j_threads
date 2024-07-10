package ru.job4j.concurrent;

/**
 * Пример установки приоритета для нити.
 * Только вызов метода start() запускает новый поток и
 * вызывает метод run() в контексте этого нового потока.
 * Приоритеты помогают JVM распределять процессорное время между нитями.
 * Приоритеты не гарантируют порядок выполнения нитей, это зависит от планировщика нитей.
 */
public class ThreadPriorities extends Thread {
    public void run() {
        System.out.println("Running thread: " + Thread.currentThread().getState());
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread();
        Thread thread2 = new Thread();
        thread1.setPriority(Thread.MIN_PRIORITY);
        thread2.setPriority(Thread.MAX_PRIORITY);
        thread1.start(); /* Запускаем поток, что приведет к вызову метода run()*/
        thread2.start(); /* Запускаем поток, что приведет к вызову метода run()*/
    }
}
