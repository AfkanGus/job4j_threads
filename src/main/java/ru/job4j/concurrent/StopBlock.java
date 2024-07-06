package ru.job4j.concurrent;

/**
 * 6. Прерывание блокированной нити. [#267413].
 * Метод join() позволяет вызывающему потоку ждать поток, у которого этот метод вызывается.
 */
public class StopBlock {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            System.out.println("start ...");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println(Thread.currentThread().isInterrupted());
                            System.out.println(Thread.currentThread().getState());
                        }
                    }
                }
        );
        progress.start();
        Thread.sleep(100);
        progress.interrupt();
        progress.join();
    }
}
