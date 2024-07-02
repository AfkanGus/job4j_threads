package ru.job4j.concurrent;

/**
 * 4. Режим ожидания. [#231217].
 * Программа ждем три секунды и печатает на консоль.
 * Работу нити можно приостановить в самой программе,для ограничения скорости работы программы.
 */
public class ThreadSleep {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        System.out.println("Начало загрузки...");
                        /*перевод нити в  TIMED_WAITING*/
                        Thread.sleep(3000);
                        System.out.println("Ожидание.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
