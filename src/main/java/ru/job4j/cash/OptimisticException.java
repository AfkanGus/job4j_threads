package ru.job4j.cash;

/**
 * 1. Неблокирующий кеш [#4741]
 */
public class OptimisticException extends Exception {
    public OptimisticException(String message) {
        super(message);
    }
}
