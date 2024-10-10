package ru.job4j.memegwait;

/**
 * 0. Управление нитью через wait. [#6858].
 */
public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            count++;
                System.out.println("Count increased: " + count);
                monitor.notifyAll();
        }
    }

    public void await() {
        synchronized (monitor) {
            while (count < total) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting.");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread thread = new Thread(countBarrier::await, "AwaitThread");
        Thread countThread = new Thread(
                () -> {
                    for (int i = 0; i < 5; i++) {
                        countBarrier.count();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                }, "CountThread"
        );
        thread.start();
        countThread.start();
        thread.join();
        countThread.join();
    }
}
