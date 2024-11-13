package com.example.sberstart_test_step.service.card.operation;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Класс реализует сервис управления операциями с балансом карты.
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OperationHandlerRepository {
    Map<String, BalanceOperationHandler> handlers = new HashMap<>();

    /**
     * Метод инициализирует коллекцию обработчиков операций с балансом.
     *
     * @param handlers Список обработчиков операций.
     */
    public OperationHandlerRepository(List<BalanceOperationHandler> handlers) {
        handlers.forEach(operationHandler -> {
            if (operationHandler.getClass().isAnnotationPresent(OperationHandler.class)) {
                String kindOperation = operationHandler.getClass().getAnnotation(OperationHandler.class).operation();

                this.handlers.put(kindOperation, operationHandler);
            }
        });
    }

    /**
     * Метод получает обработчик операции с балансом по ее типу.
     *
     * @param operationKind Тип операции с балансом.
     * @return Обработчик операции с балансом.
     */
    public BalanceOperationHandler getHandler(String operationKind) {
        return handlers.get(operationKind.toLowerCase(Locale.ROOT));
    }
}
