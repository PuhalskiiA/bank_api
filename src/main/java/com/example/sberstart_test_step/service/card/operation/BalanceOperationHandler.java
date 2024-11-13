package com.example.sberstart_test_step.service.card.operation;

import java.math.BigDecimal;

/**
 * Интерфейс определяет сервисы операций с балансом карты.
 */
public interface BalanceOperationHandler {
    /**
     * Метод изменяет баланс карты на заданную сумму.
     *
     * @param src   Текущий баланс карты.
     * @param count Сумма, на которую нужно изменить баланс.
     * @return Измененный баланс карты.
     */
    BigDecimal changeBalance(BigDecimal src, BigDecimal count);
}
