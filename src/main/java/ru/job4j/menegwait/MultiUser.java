package ru.job4j.menegwait;

/**
 * 0. Управление нитью через wait. [#6858].
 * Описание работы:
 * Поток Master запускается первым, выполняет метод barrier.on(),
 * который устанавливает флаг в true и разблокирует все потоки,
 * ожидающие на barrier.check(). Поток Slave ожидает, пока флаг
 * не станет true, и как только это происходит, продолжает
 * выполнение и выводит сообщение.
 */
public class MultiUser {
    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                   /* try {
                        Thread.sleep(2000);  Симуляция работы Master перед установкой флага
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }*/
                    barrier.on();
                    System.out.println(Thread.currentThread().getName() + " set the barrier ON");
                },
                "Master"
        );
        Thread slave = new Thread(
                () -> {
                    /*  Проверка состояния с использованием yield()
                    while (!barrier.isFlagSet()) {
                        System.out.println(Thread.currentThread().getName() + " yielding...");
                        Thread.yield();  Уступаем процессор другим потокам
                    }
                    System.out.println(Thread.currentThread()
                    .getName() + " started after barrier is ON");*/
                    barrier.check();
                    System.out.println(Thread.currentThread().getName() + " started");
                },
                "Slave"
        );
        master.start();
        slave.start();
    }
}
/**
 * Как это работает:
 * Slave многократно проверяет флаг с помощью barrier.isFlagSet(), и если флаг ещё
 * не установлен, он вызывает Thread.yield(), чтобы уступить процессор
 * другим потокам, а затем снова проверяет флаг.
 * Когда Master завершает свою работу и вызывает barrier.on(), Slave прекращает
 * цикл ожидания и выполняет нужную работу.
 * Таким образом, yield() позволяет Slave-потоку не блокироваться на мониторе,
 * а просто давать другим потокам возможность выполнения, пока он ожидает изменения состояния.
 */
