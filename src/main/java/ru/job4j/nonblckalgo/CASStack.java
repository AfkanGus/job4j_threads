package ru.job4j.nonblckalgo;

import net.jcip.annotations.ThreadSafe;
import ru.job4j.Node;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 0. CAS - операции [#6859]
 * Этот класс поддерживает CAS операции(шаблон check-then-act)
 * Чтобы сделать Stack потокобезопасным мы будем использовать класс
 * java.util.concurrent.atomic.AtomicReference
 * Здесь нет синхронизации, но класс потокобезопасный.
 */
@ThreadSafe
public class CASStack<T> {
    private final AtomicReference<Node<T>> head = new AtomicReference<>();

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        Node<T> ref;
        do {
            ref = head.get();  /*Вначале мы считываем указатель.*/
            temp.next = ref;
        } while (!head.compareAndSet(ref, temp));
    }

    public T poll() {
        Node<T> ref;
        Node<T> temp;
        do {
            ref = head.get();
            if (ref == null) {
                throw new IllegalStateException("Stack is empty");
            }
            temp = ref.next;
        } while (!head.compareAndSet(ref, temp));
        ref.next = null;
        return ref.value;
    }

    private static final class Node<T> {
        private final T value;
        private Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}
