package ru.job4j.deadlock;

/**
 * 7. Deadlock [#504910 #516263].
 * Описание:
 * В этом примере есть два класса: A и B.
 * Каждый класс имеет синхронизированные методы methodA и methodB, которые захватывают блокировки.
 * Поток t1 захватывает блокировку класса A и пытается вызвать метод last()
 * класса B, что требует блокировки класса B.
 * Поток t2 захватывает блокировку класса B и пытается вызвать метод last()
 * класса A, что требует блокировки класса A.
 * В итоге оба потока ожидают освобождения друг друга, и возникает deadlock,
 * так как ни один поток не может продолжить выполнение.
 */
public class DedLresurs {
    /*- Deadlock между взаимодействующими объектами.
     * Пример кода с взаимоблокировкой:*/
    class A {
        public synchronized void methodA(B b) {
            System.out.println("Thread in A is calling B's method");
            /*Ждем, пока класс B освободит блокировку*/
            b.last();
        }

        public synchronized void last() {
            System.out.println("Inside A's last method");
        }
    }

    class B {
        public synchronized void methodB(A a) {
            System.out.println("Thread in B is calling A's method");
            /*Ждем, пока класс A освободит блокировку*/
            a.last();
        }

        public synchronized void last() {
            System.out.println("Inside B's last method");
        }
    }

    public void main(String[] args) {
        A a = new A();
        B b = new B();

        /*Поток 1 пытается вызвать метод A и затем метод B*/
        Thread t1 = new Thread(() -> a.methodA(b));

        /*Поток 2 пытается вызвать метод B и затем метод A*/
        Thread t2 = new Thread(() -> b.methodB(a));

        t1.start();
        t2.start();
    }
}

/**
 * Решение:
 * Чтобы предотвратить deadlock, можно избегать захвата нескольких блокировок
 * одновременно или использовать открытые вызовы (вне синхронизированных методов).
 * Например, можно вынести вызов методов last() за пределы синхронизированных блоков,
 * чтобы избежать взаимной блокировки:
 * <p>
 * class A {
 * public void methodA(B b) {
 * synchronized (this) {
 * System.out.println("Thread in A is calling B's method");
 * }
 * Вызов метода B вне синхронизированного блока
 * b.last();
 * }
 * <p>
 * public synchronized void last() {
 * System.out.println("Inside A's last method");
 * }
 * }
 * <p>
 * class B {
 * public void methodB(A a) {
 * synchronized (this) {
 * System.out.println("Thread in B is calling A's method");
 * }
 *  Вызов метода A вне синхронизированного блока
 * a.last();
 * }
 * <p>
 * public synchronized void last() {
 * System.out.println("Inside B's last method");
 * }
 * }
 */
