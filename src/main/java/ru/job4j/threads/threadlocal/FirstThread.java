package ru.job4j.threads.threadlocal;

public class FirstThread extends Thread {
    public void run() {
        ThreadLocalDemo.threadLocal.set("Это поток 1");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}