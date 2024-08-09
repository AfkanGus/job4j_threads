package ru.job4j;

/**
 * 2. Модель памяти Java [#267917]
 * Ниже приведен код синглтона - double check locking.
 * без volatile оптимизация на уровне проц. и компелятора приведет к тому,
 * что instance может быть видет всем потокаим, до его инициализации.
 */
public final class DCLSingleton {
    private static volatile DCLSingleton instance;

    public DCLSingleton() {
    }

    public static DCLSingleton getInstance() {
        if (instance == null) {
            synchronized (DCLSingleton.class) {
                if (instance == null) {
                    instance = new DCLSingleton();
                }
            }
        }
        return instance;
    }
}
