package com.example.sberstart_test_step.exceptions;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorDTO> catchEntityDataNotFoundException(EntityDataNotFoundException exception) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.NOT_FOUND.value(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> catchBalanceException(BalanceException exception) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> catchCardRequisitesException(CardRequisitesException exception) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDTO> catchCustomerException(CustomerException exception) {
        return new ResponseEntity<>(new ErrorDTO(HttpStatus.BAD_REQUEST.value(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
