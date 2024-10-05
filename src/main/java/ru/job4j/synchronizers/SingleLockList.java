package ru.job4j.synchronizers;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 4. ThreadSafe динамический список  [#1105]
 * Чтобы обеспечивать потокобезопасность, нужно каждый метод сделать synchonized.
 */
@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    /*В конструктор передается ссылка на коллекцию*/
    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        /*return new ArrayList<>(list).iterator();*/
        return copy(list).iterator();
    }

    /* избежать потере ссылки нам нужно сделать копию коллекцию*/
    private List<T> copy(List<T> origin) {
        return new ArrayList<>(origin);
    }
}
