package ru.job4j.concurrent;

/**
 * 6. Прерывание блокированной нити. [#267413].
 * если используются методы sleep(), join(), wait()
 * или аналогичные временно блокирующие поток методы,
 * то нужно в блоке catch вызвать прерывание.
 */
public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        /*Прерывание потока progress из состояния Thread.sleep(500) */
        progress.interrupt();
        /*Ожидание завершения потока progress*/
        progress.join();
    }

    @Override
    public void run() {
        int count = 0;
        var process = new char[]{'-', '\\', '|', '/'};
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("\r load: " + process[count++]);
            if (count == process.length) {
                count = 0;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

