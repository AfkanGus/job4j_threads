package ru.job4j;

/* 2. Модель памяти Java [#267917]
 * Проблема видимости share visibility problem - возникает при не видимости процессоров переменых.
 * Когда основной поток обновит значение переменной в кеше проц., а второй поток будет использовать
 * старое значение.
 * -механизм синхронизации - volatile.
 * */

public class Flag {
    /*управляющий флаг для завершения работы второго потока*/
    private static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    /*в теле лямбда есть бесконечный цик, пока flag - true*/
                    while (flag) {
                        System.out.println(Thread.currentThread().getName());
                        /*цикл выводит имя поток, делает паузу на 0,5 секунд при каждой итерации*/
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        thread.start();
        /*Основной поток засыпает на 1 сек, пока второй поток выполняется*/
        Thread.sleep(1000);
        flag = false;
        /*основн поток ожидает завершения второго поток*/
        thread.join();
    }
}




