package ru.job4j.concurrent;

/*
2. Создание и запуск нити [#1016]
Для создания еще одной нити воспользуемся классом java.lang.Thread;
Thread.currentThread().getName() - стат.метот.для получ. экз.текущ.нити выполнения.
start() - указывает вертуальной машине что операторы из конструктора запустить в отдельной нити.
Конструктор класса Thread ринимает функциональный интерфейс java.lang.Runnable.
интерфейс имеет один метод public void run(). методы в этом методе будут выполнятся
в многозадачной среде run вызывает операторы в той же нити, в которой запущен этот метод.
another.start(); - если убрать опуратор то вывод имени второй нити не будет.
another.run() - нити и в первом и во втором случае одинаковое. Это происходит,
потому что метод run не дает указания выполнить свои операторы
в отдельной нити, как это делаем метод
 */

public class ConcurrentOutput {
    public static void main(String[] args) {
        Thread another = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        another.start(); /*если убрать опуратор то вывод имени второй нити не будет*/
        /*another.run();*/
        System.out.println(Thread.currentThread().getName());

        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        System.out.println(Thread.currentThread().getName());
    }
}
