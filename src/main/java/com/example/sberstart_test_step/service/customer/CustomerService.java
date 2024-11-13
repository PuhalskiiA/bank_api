package com.example.sberstart_test_step.service.customer;

import com.example.sberstart_test_step.dto.customer.CustomerDTOResponse;
import com.example.sberstart_test_step.dto.customer.CustomerDTOCreateRequest;

import java.util.UUID;

/**
 * Интерфейс определяет сервисы работы с клиентами.
 */
public interface CustomerService {
    /**
     * Метод получает информацию о клиенте по его номеру телефонв.
     *
     * @param phone Номер клиента.
     * @return Информация о клиенте.
     */
    CustomerDTOResponse getCustomerInfo(String phone);

    /**
     * Метод получает информацию о клиенте по его идентификатору.
     *
     * @param customerId Идентификатор клиента.
     * @return Информация о клиенте.
     */
    CustomerDTOResponse getCustomerInfo(UUID customerId);

    /**
     * Метод создает нового клиента.
     *
     * @param customerDTORequest Данные для создания клиента.
     * @return Информация о созданном клиенте.
     */
    CustomerDTOResponse createCustomer(CustomerDTOCreateRequest customerDTORequest);

    /**
     * Метод удаляет клиента.
     *
     * @param customerId Информация о клиенте.
     * @return Информация о удаленном клиенте.
     */
    CustomerDTOResponse deleteCustomer(UUID customerId);

    /**
     * Метод обновляет информацию о клиенте.
     *
     * @param customerDTOResponse Информация о клиенте.
     * @return Информация об обновленном клиенте.
     */
    CustomerDTOResponse updateCustomer(CustomerDTOResponse customerDTOResponse);
}
