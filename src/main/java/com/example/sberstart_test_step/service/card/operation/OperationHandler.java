package com.example.sberstart_test_step.service.card.operation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Аннотация используется для определения типа операции с балансом карты.
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface OperationHandler {
    String operation();
}
