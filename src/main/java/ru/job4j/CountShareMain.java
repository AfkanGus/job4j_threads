package ru.job4j;

/**
 * Что такое атамарность?.
 * Атомарность - это свойство определяющее группу операций, которые выполняются неразрывно.
 * Операция инкремента счётчика не атомарна. Определить не атомарные операции просто.
 * Все операции, где данные зависят от начального состояния не атомарны.
 * Эти операции можно описать через процесс "проверить и выполнить".
 */
public class CountShareMain {
    public static void main(String[] args) throws InterruptedException {
        Count count = new Count();
        Thread first = new Thread(count::increment);
        Thread second = new Thread(count::increment);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(count.get());
    }
}
