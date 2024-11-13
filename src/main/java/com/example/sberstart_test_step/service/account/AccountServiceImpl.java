package com.example.sberstart_test_step.service.account;

import com.example.sberstart_test_step.dto.AccountDTO;
import com.example.sberstart_test_step.dto.customer.CustomerDTOResponse;
import com.example.sberstart_test_step.exceptions.EntityDataNotFoundException;
import com.example.sberstart_test_step.mapper.AccountDTOMapper;
import com.example.sberstart_test_step.mapper.CustomerDTOResponseMapper;
import com.example.sberstart_test_step.model.base.Account;
import com.example.sberstart_test_step.repository.AccountRepository;
import com.example.sberstart_test_step.service.customer.CustomerService;
import com.example.sberstart_test_step.utils.Status;
import com.example.sberstart_test_step.utils.number.NumberGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис для работы со счетами.
 **/
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountServiceImpl implements AccountService {
    @Value("${account.number.count}")
    @NonFinal
    Integer ACCOUNT_NUMBER_COUNT;

    AccountRepository accountRepository;
    CustomerService customerServiceImpl;

    NumberGenerator accountNumberGeneratorImpl;

    /**
     * Метод получает список счетов клиента по его идентификатору.
     *
     * @param customerId Идентификатор клиента.
     * @return Список счетов клиента.
     */
    @Override
    public List<AccountDTO> getAccountsByCustomerId(UUID customerId) {
        return accountRepository.getAccountsByCustomerId(customerId).stream()
                .map(AccountDTOMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод получает информацию о счете по его идентификатору.
     *
     * @param accountId Идентификатор аккаунта.
     * @return Информация о счете.
     */
    @Override
    public AccountDTO getAccount(UUID accountId) {
        return AccountDTOMapper.INSTANCE.toDto(accountRepository.getAccountById(accountId)
                .orElseThrow(() -> new EntityDataNotFoundException("Счет не найден")));
    }

    /**
     * Метод создает новый счет для клиента.
     *
     * @param customerId Идентификатор клиента.
     * @return Информация о созданном счете.
     */
    @Override
    @Transactional
    public AccountDTO createAccount(UUID customerId) {
        CustomerDTOResponse response = customerServiceImpl.getCustomerInfo(customerId);

        Account account = getAccountWithProperties(response);

        accountRepository.createAccount(account.getId(),
                account.getNumber(),
                Date.valueOf(account.getRelease()),
                account.getCustomer().getId(),
                Date.valueOf(account.getCreated()),
                Date.valueOf(account.getUpdated()),
                account.getStatus().name()
        );

        return AccountDTOMapper.INSTANCE.toDto(account);
    }

    /**
     * Метод удаляет счет клиента по его идентификатору.
     *
     * @param accountId Идентификатор счета.
     * @return Информация об удаленном счете.
     */
    @Override
    @Transactional
    public AccountDTO deleteAccount(UUID accountId) {
        AccountDTO account = getAccount(accountId);

        accountRepository.deleteAccountById(accountId, Date.valueOf(LocalDate.now()), Status.DELETED.name());

        return account;
    }

    /**
     * Метод создает новый счет для клиента с заданными свойствами.
     *
     * @param customerDTOResponse Данные клиента.
     * @return Информация о созданном счете.
     */
    private Account getAccountWithProperties(CustomerDTOResponse customerDTOResponse) {
        LocalDate release = LocalDate.now();

        return new Account(
                UUID.randomUUID(),
                accountNumberGeneratorImpl.generateNumber(ACCOUNT_NUMBER_COUNT),
                release,
                CustomerDTOResponseMapper.INSTANCE.toModel(customerDTOResponse),
                Collections.emptyList(),
                release,
                release,
                Status.ACTIVE
        );
    }
}
