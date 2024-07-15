package ru.job4j.threads.threadlocal;

/**
 * ба потока работают с одним и тем же объектом, изменение имени
 * в первом потоке также повлияет на второй поток,
 * это приведет к ошибке ассинхрации.
 */
public class ThreadLocalAsinxron {
    public static void main(String[] args) {
        ThreadLocal<Pr> threadLocal = new ThreadLocal<>();
        Runnable task1 = () ->
        {
            Pr person = new Pr("John", 30);
            threadLocal.set(person);
            person.name = "Bod";
        };
        /*второй поток*/
        Runnable task2 = () -> {
            Pr person = threadLocal.get();
            person.age = 40;
        };
        new Thread(task1).start();
        new Thread(task2).start();
    }
}
