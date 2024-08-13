package ru.job4j;

/**
 * 3. Immutable объекты [#267918].
 *  неизменяемость достигается через final полей и инициализации
 *  конструктора. Методы setNext ,setValue удалены т.к.обект не может быть
 *  изменен - это исключает состояние гонку.
 */
public class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public T getValue() {
        return value;
    }
}
