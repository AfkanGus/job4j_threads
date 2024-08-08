package ru.job4j.threads.threadlocal;

/**
 * Будьте внимательны, так как ThreadLocal изолирует только ссылки на объекты.
 * ThreadLocal - это переменная, копия которой доступна локально внутри
 * каждой нити. ThreadLocal отличается от своих обычных аналогов-переменных
 * тем, что каждая нить, обращающаяся к ThreadLocal, будет иметь свою
 * независимо инициализированную копию переменной.
 */

public class ThreadLocalDemo {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static ThreadLocal<String> getThreadLocal() {
        return threadLocal;
    }

    public static void setThreadLocal(ThreadLocal<String> threadLocal) {
        ThreadLocalDemo.threadLocal = threadLocal;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread first = new FirstThread();
        Thread second = new SecondThread();
        threadLocal.set("Это поток main");
        System.out.println(threadLocal.get());
        first.start();
        second.start();
        first.join();
        second.join();
    }
}
