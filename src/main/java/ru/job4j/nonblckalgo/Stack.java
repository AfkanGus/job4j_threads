package ru.job4j.nonblckalgo;

/**
 * 0. CAS - операции [#6859]
 * Процессоры на уровне ядра поддерживают операцию
 * compare-and-swap. Эта операция атомарная.
 *
 * @param <T>
 *  Рассмотрим не потокобезопасный Stack
 *  т.к. он без синхронизации
 */
public class Stack<T> {
    private Node<T> head;

    public void push(T value) {
        Node<T> temp = new Node<>(value);
        if (head == null) {
            head = temp;
            return;
        }
        temp.next = head;
        head = temp;
    }

    public T poll() {
        Node<T> temp = head;
        if (temp == null) {
            throw new IllegalStateException("Stack is empty");
        }
        head = temp.next;
        temp.next = null;
        return temp.value;
    }

    private static final class Node<T> {
        private final T value;

        private Node<T> next;

        public Node(final T value) {
            this.value = value;
        }
    }
}
