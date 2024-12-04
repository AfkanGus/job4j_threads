package ru.job4j.cash;

/**
 * 1. Неблокирующий кеш [#4741]
 */
public class OptimisticException extends RuntimeException {
    public OptimisticException(String message) {
        super(message);
    }
}
