package com.example.sberstart_test_step.controller;

import com.example.sberstart_test_step.dto.AccountDTO;
import com.example.sberstart_test_step.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Класс реализует контроллер для работы с счетами.
 */
@RestController
@RequestMapping("/accounts")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Tag(name = "Account controller", description = "Контроллер для работы со счетами")
public class AccountController {
    AccountService accountServiceImpl;

    /**
     * Метод получает список счетов клиента по его идентификатору.
     *
     * @param customerId Идентификатор клиента.
     * @return Список счетов клиента.
     */
    @Operation(
            summary = "Получить список счетов клиента по его идентификатору",
            description = "Возвращает список счетов клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список счетов клиента"),
    })
    @GetMapping(value = "/customer/{customer_id}")
    public List<AccountDTO> getAccountsByCustomerId(@PathVariable("customer_id") UUID customerId) {
        return accountServiceImpl.getAccountsByCustomerId(customerId);
    }

    /**
     * Метод получает информацию о счете по его идентификатору.
     *
     * @param accountId Идентификатор счета.
     * @return Информация о счете.
     */
    @Operation(
            summary = "Получить информацию о счете по его идентификатору",
            description = "Возвращает информацию о счете")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о счете"),
            @ApiResponse(responseCode = "404", description = "Счет не найден")
    })
    @GetMapping(value = "/{account_id}")
    public AccountDTO getAccount(@PathVariable("account_id") UUID accountId) {
        return accountServiceImpl.getAccount(accountId);
    }

    /**
     * Метод создает новый счет.
     *
     * @param customerId Данные счета.
     * @return Информация о созданном счете.
     */
    @Operation(
            summary = "Создать новый счет",
            description = "Создает новый счет")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Создан новый счет"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PostMapping(value = "/{customer_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDTO createAccount(@PathVariable("customer_id") UUID customerId) {
        return accountServiceImpl.createAccount(customerId);
    }

    /**
     * Метод удаляет счет.
     *
     * @param accountId Информация о счете.
     * @return Информация об удаленном счете.
     */
    @Operation(
            summary = "Удалить счет",
            description = "Удаляет счет")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Счет успешно удален"),
            @ApiResponse(responseCode = "404", description = "Счет не найден")
    })
    @DeleteMapping(value = "/{account_id}")
    public AccountDTO deleteAccount(@PathVariable("account_id") UUID accountId) {
        return accountServiceImpl.deleteAccount(accountId);
    }
}