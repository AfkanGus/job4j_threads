package ru.job4j.synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 6. Синхронизаторы [#504906].
 */
public class CyclicBarrierExample {
    public static void main(String[] args) {
        /*Создаем CyclicBarrier с числом 4, то есть 4 потока должны собраться.*/
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            /*Действие, которое выполняется, когда все потоки достигли барьера.*/
            System.out.println("Все грузчики собраны. Ящик можно поднимать!");
        });
        /*Создаем 4 рабочих потока (грузчиков).*/
        for (int i = 1; i <= 4; i++) {
            final int workerId = i;
            new Thread(() -> {
                try {
                    System.out.println("Грузчик " + workerId + " готов поднять ящик.");
                    /*Ждем, пока все грузчики не будут готовы.*/
                    barrier.await(); /*Поток блокируется до тех пор, пока не все
                     грузчики не достигнут барьера.*/
                    /*Здесь грузчики поднимают ящик.*/
                    System.out.println("Грузчик " + workerId + " поднимает ящик!");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
/**
 * Что здесь происходит:
 * CyclicBarrier barrier = new CyclicBarrier(4, () -> {...}); — создается объект CyclicBarrier,
 * который требует, чтобы 4 потока достигли барьера, прежде чем выполнение продолжится.
 * Также передается действие (лямбда-выражение), которое будет выполнено,
 * когда все 4 потока достигнут барьера.
 * <p>
 * В цикле создаются 4 рабочих потока (грузчиков). Каждый поток:
 * <p>
 * System.out.println("Грузчик " + workerId + " готов поднять ящик.");
 * — сообщает о своей готовности.
 * barrier.await(); — блокируется, ожидая других грузчиков. Как только все 4 грузчика
 * достигнут этой точки, действие в барьере будет выполнено (вывод сообщения о том,
 * что все грузчики собраны), и все потоки продолжат выполнение.
 * System.out.println("Грузчик " + workerId + " поднимает ящик!"); — грузчики поднимают ящик.
 * <p>
 * Технические различия:
 * CyclicBarrier позволяет многократное использование, в отличие
 * от CountDownLatch, который можно использовать только один раз.
 * CyclicBarrier учитывает количество вызовов await(),
 * а CountDownLatch — количество вызовов countdown().
 * CyclicBarrier может выполнять действия после завершения всех потоков,
 * в то время как CountDownLatch просто разблокирует потоки, когда счетчик достигает нуля.
 */
