package ru.job4j.synchronizers;

import java.util.concurrent.Exchanger;

/**
 * 6. Синхронизаторы [#504906].
 */
public class GiftExchange {
    public static void main(String[] args) {
        /*Создание обменника для обмена строками (подарками).*/
        Exchanger<String> exchanger = new Exchanger<>();

        /*Поток 1 (ваш поток)*/
        Thread thread1 = new Thread(() -> {
            try {
                String myGift = "Подарок для родственников"; /*Ваш подарок*/
                /*Ожидание и обмен подарками*/
                String receivedGift = exchanger.exchange(myGift);
                System.out.println("Я получил: " + receivedGift);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        /*Поток 2 (брат)*/
        Thread thread2 = new Thread(() -> {
            try {
                String brotherGift = "Подарок от родственников"; /*Подарок от родственников*/
                /*Ожидание и обмен подарками*/
                String receivedGift = exchanger.exchange(brotherGift);
                System.out.println("Брат получил: " + receivedGift);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        /*Запуск потоков*/
        thread1.start();
        thread2.start();
    }
}
/**
 * Exchanger<V>.
 * <p>
 * Обменник. Предназначен для упрощения процесса обмена данными между двумя потоками в
 * определенной стадии работы потоков. Exchanger ожидает до тех пор, пока два отдельных
 * потока не вызовут его метод exchange(). После этого происходит обмен данными,
 * предоставленными обоими потоками. Exchanger служит для синхронизации обмена
 * данными между классами программы. Важной его особенностью является то, что он не
 * завершится успешно до тех пор, пока не будет вызван метод exchange() у одного и
 * того же объекта типа Exchanger из двух разных нитей. Таким образом, это очень простой,
 * надежный и удобный в применении механизм синхронизации обмена данными.
 * <p>
 * Exchanger - это обобщенный класс. Exchanger<V>. V - тип обмениваемых данных.
 * <p>
 * Пример из жизни: Брат едет на другой конец города, где живут ваши родственники.
 * Вы отдали брату подарки для родственников и ждете, когда вам придут подарки от них в ответ.
 * Брат приехал, обменял подарки и по приезду передал вам. Exchanger может и не передавать данные
 * с одной из сторон (null), а просто передать данные одной нити - другой нити.
 */