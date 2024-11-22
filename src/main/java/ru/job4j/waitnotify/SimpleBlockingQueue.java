package ru.job4j.waitnotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 1. Реализовать шаблон Producer Consumer. [#1098]
 * В задании нельзя использовать потокобезопасные коллекции
 * реализованные в JDK. Ваша задача используя,
 * wait/notifyAll реализовать блокирующую очередь.
 * Эта блокирующая очередь,ограниченая по размеру.
 * Producer помещает данную очередь,Consumer извелкает данные из очереди.
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final Object monitor = this;
    private final int maxLimitQueue;
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int maxLimitQueue) {
        this.maxLimitQueue = maxLimitQueue;
    }

    /*метод добавления элемена в очеред для Producer*/
    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() == maxLimitQueue) {
                /*освобождаем манитор и переводим поток в сост.ожидания*/
                monitor.wait();
            }
            queue.offer(value);
            monitor.notifyAll();
        }
    }

    /* Метод для извлечения элемента из очереди*/
    public T poll() throws InterruptedException {
        T rsl;
        synchronized (this) {
            while (queue.isEmpty()) {
                monitor.wait();
            }
            /*извлекаем эл.из начало очереди*/
            rsl = queue.poll();
            monitor.notifyAll();
            return rsl; /*метод вернет обкт из клкц*/
        }
    }

    public synchronized boolean isEmpty() {
       return queue.isEmpty();
    }
}

