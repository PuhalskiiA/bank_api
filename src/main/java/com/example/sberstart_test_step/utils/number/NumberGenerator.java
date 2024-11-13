package com.example.sberstart_test_step.utils.number;

/**
 * Интерфейс определяет сервисы генерации номеров.
 */
public interface NumberGenerator {
    /**
     * Метод генерирует строку с заданным количеством чисел.
     *
     * @param count Количество чисел, которое нужно сгенерировать.
     * @return Строка с числами.
     */
    String generateNumber(Integer count);
}
