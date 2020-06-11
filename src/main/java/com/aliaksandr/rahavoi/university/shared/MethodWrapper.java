package com.aliaksandr.rahavoi.university.shared;

/**
 * Утилитарный класс, который дает полезные методы
 * для работы с другими методами.
 */
public final class MethodWrapper {
    private MethodWrapper() {
    }

    /**
     * Оборачивает вызов произвольного метода в
     * try-catch блок и игнорирует любые ошибки,
     * возникшие во время исполнения программы.
     */
    public static void ignoreException(Runnable runnable) {
        try {
            runnable.run();
        } catch (RuntimeException ignored) {
            //ignoring exception
        }
    }
}
