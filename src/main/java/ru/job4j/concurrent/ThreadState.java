package ru.job4j.concurrent;

/**
 * 3. Состояние нити. [#229175]
 * в этой программе присутствуют две нити: main и first. Нить main - это нить,
 * которая запускается, когда мы запускаем метод main. Нить first мы создали сами.
 */
public class ThreadState {
    public static void main(String[] args) {
        /* new создан обеъкт нити с лямбда с пустым методом run. состояние new*/
        Thread first = new Thread(
                () -> {
                }
        );
        /*печать текущего состояние  new, потока first*/
        System.out.println(first.getState());
        /*Поток first запускается, и его состояние меняется на RUNNABLE.*/
        first.start();
        /*Главная нить исполнения main будет работать до тех пор,
        пока нить first не завершит свою работу - TERMINATED.*/
        while (first.getState() != Thread.State.TERMINATED) {
            /*печать текущего состояния*/
            System.out.println(first.getState());
        }
        /*Чтобы получить состояние нити можно воспользоваться методом - getState().*/
        System.out.println(first.getState());
    }
}
