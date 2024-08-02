package ru.job4j;

/**
 * 0. Что такое атомарность? [#6856].
 * Является ли этот код атомарным?
 *   Нет т.к getInstance -  состоит из нескольких операций, которые не выполняются как единое целое
 *  Эти операции включают в себя проверку на null, создание
 *  нового объекта и присвоение ссылки переменной cache.
 */
public class Cache {
    private static Cache cache;

    public static Cache getInstance() {
        if (cache == null) {
            cache = new Cache();
        }
        return cache;
    }
}
