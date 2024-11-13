package com.example.sberstart_test_step.service.card.operation;

import com.example.sberstart_test_step.exceptions.BalanceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Класс реализует сервис уменьшения баланса карты.
 */
@Service
@OperationHandler(operation = "decrease")
public class DecreaseBalanceOperationHandler implements BalanceOperationHandler {
    /**
     * Метод уменьшает баланс карты на заданную сумму.
     *
     * @param src   Текущий баланс карты.
     * @param count Сумма, на которую нужно уменьшить баланс.
     * @return Измененный баланс карты.
     */
    @Override
    public BigDecimal changeBalance(BigDecimal src, BigDecimal count) {
        if (count.signum() < 1) {
            throw new BalanceException("Входное денежное значение должно быть больше нуля");
        }

        return src.subtract(count);
    }
}
