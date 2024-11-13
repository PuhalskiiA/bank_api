package com.example.sberstart_test_step.utils.number;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Класс реализует сервис генерации номеров карт.
 */
@Component
public class CardNumberGeneratorImpl implements NumberGenerator {
    /**
     * Метод генерирует строку с заданным количеством чисел.
     *
     * @param count Количество чисел, которое нужно сгенерировать.
     * @return Строка с числами.
     */
    @Override
    public String generateNumber(Integer count) {
        StringBuffer sb = new StringBuffer();

        IntStream.range(0, count).forEach(item -> sb.append(ThreadLocalRandom.current().nextInt(0, 9)));

        return sb.toString();
    }
}
