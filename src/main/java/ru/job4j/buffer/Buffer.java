package ru.job4j.buffer;

/**
 * 0. Монитор, мьютекс, критическая секция [#6857 #511497]
 * Когда метод объявлен с synchronized, он автоматически использует монитор текущего объекта,
 * т.е., экземпляра класса Buffer. this не указан явно, но подразумевается.
 */
public class Buffer {
    private StringBuilder buffer = new StringBuilder();
    /* new Buffer (Объект класса buffer) - monitor */

    public void add(int value) { /* код внитри блока - критич секция */
        synchronized (this) { /* this -  это текущий экземпляр класса Buffer - monitor */
            System.out.print(value);
            buffer.append(value);
        }
    }

    @Override
    public String toString() { /* критич секция */
        synchronized (this) { /*this — это текущий экземпляр объекта Buffer*/
            return buffer.toString();
        }
    }
}
