package com.example.sberstart_test_step.service.card.operation;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Класс реализует сервис увеличения баланса карты.
 */
@Service
@OperationHandler(operation = "increase")
public class IncreaseBalanceOperationHandler implements BalanceOperationHandler {
    /**
     * Метод увеличивает баланс карты на заданную сумму.
     *
     * @param src   Текущий баланс карты.
     * @param count Сумма, на которую нужно увеличить баланс.
     * @return Измененный баланс карты.
     */
    @Override
    public BigDecimal changeBalance(BigDecimal src, BigDecimal count) {
        return src.add(count);
    }
}
