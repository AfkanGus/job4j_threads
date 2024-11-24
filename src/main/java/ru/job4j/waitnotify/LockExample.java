package ru.job4j.waitnotify;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 4. Блокировки [#504911 #523192].
 * Роль ReentrantLock:Гарантирует, что только один поток сможет
 * получить доступ к ресурсу, защищённому этой блокировкой.
 * Позволяет другим потокам ожидать освобождения ресурса.
 * Объект ReentrantLock поддерживает повторный захват блокировки одним
 * и тем же потоком. Если поток уже владеет блокировкой, он может захватить
 * её повторно, что особенно полезно при реализации рекурсивных алгоритмов.
 */
public class LockExample {
    private static class SharedResource {
        private int value = 0;
        /*сздт обкт  блкрвки для зациты общего ресурса от одновременного доступа*/
        private final Lock lock = new ReentrantLock();

        /**
         * Увеличение значения с использование Lock
         * Метод increment:
         * <p>
         * Захватывает блокировку перед изменением значения.
         * Освобождает блокировку в блоке finally, чтобы гарантировать
         * её освобождение даже в случае исключений.
         */
        public void increment() {
            /*захват блокировки*/
            lock.lock();
            try {
                value++;
                System.out.println(Thread.currentThread().getName()
                        + "incremented value to " + value);
            } finally {
                /*освободжение блокировки*/
                lock.unlock();
            }
        }

        /**
         * Чтение значения с использованием tryLock
         * Метод printValue:
         * <p>
         * Использует метод tryLock(), чтобы избежать блокировки, если она недоступна.
         * Если блокировка не может быть захвачена, поток просто выводит сообщение.
         */
        public void printValue() {
            if (lock.tryLock()) { /* Пытаемся захватить блокировку*/
                try {
                    System.out.println(Thread.currentThread().getName() + " read value: " + value);
                } finally {
                    lock.unlock(); /*Освобождаем блокировку*/
                }
            } else {
                System.out.println(Thread.currentThread().getName()
                        + " could not acquire lock to read value.");
            }
        }

        /**
         * Пример использования lockInterruptibly
         * Метод resetValue:
         * <p>
         * Использует метод lockInterruptibly() для получения блокировки.
         * Если поток будет прерван, пока ожидает
         * блокировки, он сможет выйти из ожидания.
         */

        public void resetValue() throws InterruptedException {
            lock.lockInterruptibly(); /*Ожидание блокировки с возможностью прерывания*/
            try {
                value = 0;
                System.out.println(Thread.currentThread().getName() + " reset value to " + value);
            } finally {
                lock.unlock(); /*Освобождение блокировки*/
            }
        }

        public static void main(String[] args) {
            SharedResource resource = new SharedResource();

            /*Поток для увеличения значения*/
            Thread incrementThread = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    resource.increment();
                    try {
                        Thread.sleep(100); /*Имитация работы*/
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            /*Поток для чтения значения*/
            Thread readThread = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    resource.printValue();
                    try {
                        Thread.sleep(50); /* Имитация работы*/
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            /*Поток для сброса значения*/
            Thread resetThread = new Thread(() -> {
                try {
                    Thread.sleep(200); /*Даем возможность другим потокам захватить блокировку*/
                    resource.resetValue();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " was interrupted.");
                }
            });

            incrementThread.start();
            readThread.start();
            resetThread.start();

            try {
                incrementThread.join();
                readThread.join();
                resetThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

