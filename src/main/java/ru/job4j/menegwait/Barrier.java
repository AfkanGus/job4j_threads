package ru.job4j.menegwait;

/**
 * 0. Управление нитью через wait. [#6858].
 */
public class Barrier {
    private boolean flag = false;
    private final Object monitor = this;

    public void on() {
        synchronized (monitor) {
            flag = true;
            /*Метод notifyAll будит все нити, которые ждали изменения состояния.*/
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    /**
     * поток, вызвавший этот метод, блокируется до тех пор, пока флаг не станет true.
     */
    public void check() {
        synchronized (monitor) {
            while (!flag) {
                try {
                    /*wait переводит нить в ожидания, если программа не может дальше выполняться.*/
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /*метод isFlagSet(), чтобы потоки могли проверять, установлен ли флаг,
    без необходимости блокировки монитора.*/
    public boolean isFlagSet() {
        synchronized (monitor) {
            return flag;
        }
    }
}
