package ru.job4j.concurrent;

/**
 * 5. Прерывание нити [#1019].
 * Демонстрация преривыния выполнения нити.
 * interrupt()- метод выставляет флаг прерывания.
 * isInterrupted() - проверить состояние флага.
 * Флаг прерывания — это boolean-переменная, ассоциированная с каждым потоком,
 * которая указывает, был ли поток прерван
 * Если поток не был прерван, isInterrupted() возвращает false, и цикл продолжается.
 * Если поток был прерван (то есть вызван метод interrupt()),
 * isInterrupted() возвращает true, и цикл завершится.
 * interrupted()- сбрасывает статус прерывания после проверки.
 */
public class ThreadStop {
    /*основной поток выполняется в методе main*/
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(/*создаем второй поток*/
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }
                }
        );

        thread.start();  /*Запуск второго потока внутри лямбды*/
        Thread.sleep(1000);  /*Главный поток засыпает на 1 с.*/
        /* Основной поток прерывает второй поток выст флага для второго потока*/
        thread.interrupt();
    }
}
