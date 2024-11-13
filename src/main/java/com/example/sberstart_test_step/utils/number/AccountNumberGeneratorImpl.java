package com.example.sberstart_test_step.utils.number;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Класс реализует сервис генерации номеров счетов.
 */
@Component
public class AccountNumberGeneratorImpl implements NumberGenerator {
    /**
     * Метод генерирует строку с заданным количеством цифр.
     *
     * @param count Количество цифр, которое нужно сгенерировать.
     * @return Строка с цифрами.
     */
    @Override
    public String generateNumber(Integer count) {
        StringBuffer sb = new StringBuffer();

        IntStream.range(0, count).forEach(item -> sb.append(ThreadLocalRandom.current().nextInt(0, 5)));

        return sb.toString();
    }
}
