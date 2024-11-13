package com.example.sberstart_test_step.service.account;

import com.example.sberstart_test_step.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс определяет сервисы работы с аккаунтами клиентов.
 */
public interface AccountService {
    /**
     * Метод получает список счетов клиента по его идентификатору.
     *
     * @param customerId Идентификатор клиента.
     * @return Список счетов клиента.
     */
    List<AccountDTO> getAccountsByCustomerId(UUID customerId);

    /**
     * Метод получает информацию о счете по его идентификатору.
     *
     * @param accountId Идентификатор счета.
     * @return Информация о счете.
     */
    AccountDTO getAccount(UUID accountId);

    /**
     * Метод создает новый счет для клиента.
     *
     * @param customerId Идентификатор клиента.
     * @return Информация о созданном счете.
     */
    AccountDTO createAccount(UUID customerId);

    /**
     * Метод удаляет счет клиента по его идентификатору.
     *
     * @param accountId Идентификатор счета.
     * @return Информация об удаленном счете.
     */
    AccountDTO deleteAccount(UUID accountId);
}
