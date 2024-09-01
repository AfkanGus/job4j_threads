package ru.job4j.buffer;

/**
 * 0. Монитор, мьютекс, критическая секция [#6857 #511497].
 * Когда метод объявлен с synchronized, он автоматически использует монитор текущего объекта,
 * т.е., экземпляра класса Buffer. this не указан явно, но подразумевается.
 */
public class Buffer {
    private StringBuilder buffer = new StringBuilder();

    /* 'this' — это текущий экземпляр объекта Buffer*/
    public synchronized void add(int value) {
        System.out.print(value);
        buffer.append(value);
    }

    /*'this' — это текущий экземпляр объекта Buffer*/
    @Override
    public synchronized String toString() {
        return buffer.toString();
    }
}
