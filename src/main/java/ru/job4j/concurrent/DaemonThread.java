package ru.job4j.concurrent;

/**
 * Нити-демоны
 * Нити-демоны часто используются для выполнения фоновых задач,
 * таких как сборка мусора, логирование и другие задачи,
 * не требующие завершения до завершения основной программы.
 * В этом примере, после завершения основной нити (через 3 секунды),
 * нить-демон также завершится, даже если она ещё работает.
 */
public class DaemonThread extends Thread {
    public void run() {
        while (true) {
            System.out.println("Daemon thread running...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); //устанавливает нит как демон
        daemonThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Main thread ending..");
    }
}
