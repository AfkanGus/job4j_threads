package ru.job4j.concurrent;

public class DebugConcurrentDemo {
    /*здесь в главном потоке запускаетсе еще 2 потока*/
    public static void main(String[] args) {
        String name = "First thread";
        String name1 = "Second thread";
        /*в лямбде код выполняемый в новом потоке*/
        Thread t1 = new Thread(() -> {
            try {
                for (int i = 3; i > 0; i--) {
                    System.out.println(Thread.currentThread().getName() + ";" + i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " bread");
            }
            System.out.println(Thread.currentThread().getName() + " end");
        }, name);
        Thread t2 = new Thread(() -> {
            try {
                for (int i = 10; i < 13; i++) {
                    System.out.println(Thread.currentThread().getName() + ";" + i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " bread");
            }
            System.out.println(Thread.currentThread().getName() + " ebd");
        }, name1);
        /*запуск потоков и они будут выполнятся паралельно с главным потоком*/
        t1.start();
        t2.start();
        try {
            /*главный поток в котором выполн метод main приостанавл на 100 милсек*/
            Thread.sleep(100);
        } catch (InterruptedException e) {
            /*если во проемя сна поток будет прерван поймается исключение*/
            System.out.println("main thread is break");
        }
        System.out.println("main thread is end");
    }
}
/**
 * 11. Отладка в многопоточном приложении [#504954 #507315].
 * Откладка в многопоточных прилож. предстовляет
 * откладку как отдельное однопоточное приложение.
 * Поток в котором запущен откладчик, работает в однопоточном режиме.
 */