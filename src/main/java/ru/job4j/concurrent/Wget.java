package ru.job4j.concurrent;

/**
 * п
 * Программа выводит через 1 секунду
 * на консоль информацию о загрузке с обновлением строки.
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 100; i++) {
                            /* \r - каретка будет возвращаться в начало строки.*/
                            System.out.print("\rLoading : " + i + "%");
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.start();
        System.out.println("Main");
    }
}
