package ru.job4j.threads.threadlocal;

public class SecondThread extends Thread {
    public void run() {
        ThreadLocalDemo.threadLocal.set("Это нить 2");
        System.out.println(ThreadLocalDemo.threadLocal.get());
    }
}
