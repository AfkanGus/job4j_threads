package ru.job4j.concurrent;

/**
 * 4. Режим ожидания. [#231217].
 * Программа выводит через 1 секунду
 * на консоль информацию о загрузке с обновлением строки.
 */
public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () ->
                {
                    try {
                        for (int i = 0; i < 100; i++) {
                            /*Символ \r указывает, что каретку каждый раз нужно вернуть в начало строки.*/
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
