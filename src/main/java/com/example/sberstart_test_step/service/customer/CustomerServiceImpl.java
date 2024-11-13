package com.example.sberstart_test_step.service.customer;

import com.example.sberstart_test_step.dto.customer.CustomerDTOResponse;
import com.example.sberstart_test_step.dto.customer.CustomerDTOCreateRequest;
import com.example.sberstart_test_step.exceptions.CustomerException;
import com.example.sberstart_test_step.exceptions.EntityDataNotFoundException;
import com.example.sberstart_test_step.mapper.CustomerDTOResponseMapper;
import com.example.sberstart_test_step.model.base.Customer;
import com.example.sberstart_test_step.repository.CustomerRepository;
import com.example.sberstart_test_step.utils.Role;
import com.example.sberstart_test_step.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для работы с пользователями.
 **/
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;

    /**
     * Получает информацию о пользователе по его номеру телефона.
     *
     * @param phone номер телефона пользователя
     * @return информация о пользователе
     */
    @Override
    public CustomerDTOResponse getCustomerInfo(String phone) {
        return CustomerDTOResponseMapper.INSTANCE.toDto(customerRepository.getCustomerByPhone(phone)
                .orElseThrow(() -> new EntityDataNotFoundException("Пользователь не найден")));
    }

    /**
     * Получает информацию о пользователе по его ID.
     *
     * @param customerId идентификатор пользователя
     * @return информация о пользователе
     */
    @Override
    public CustomerDTOResponse getCustomerInfo(UUID customerId) {
        return CustomerDTOResponseMapper.INSTANCE.toDto(customerRepository.getCustomer(customerId)
                .orElseThrow(() -> new EntityDataNotFoundException("Пользователь не найден")));
    }

    /**
     * Создает нового пользователя.
     *
     * @param customerDTORequest данные для создания пользователя
     * @return информация о созданном пользователе
     * @throws CustomerException если пользователь с такими данными уже существует
     */
    @Override
    @Transactional
    public CustomerDTOResponse createCustomer(CustomerDTOCreateRequest customerDTORequest) {
        Optional<Customer> customer = customerRepository.getCustomerByFirstNameAndLastNameAndPassport(customerDTORequest.getFirstName(),
                customerDTORequest.getLastName(), customerDTORequest.getPassport());

        if (customer.isPresent()) {
            throw new CustomerException("Пользователь уже существует");
        }

        Customer resultCustomer = getCustomerWithProperties(customerDTORequest);

        customerRepository.createCustomer(resultCustomer.getId(),
                resultCustomer.getFirstName(),
                resultCustomer.getLastName(),
                resultCustomer.getRole().name(),
                resultCustomer.getEmail(),
                resultCustomer.getPhone(),
                resultCustomer.getPassport(),
                Date.valueOf(resultCustomer.getBirthday()),
                Date.valueOf(resultCustomer.getCreated()),
                Date.valueOf(resultCustomer.getUpdated()),
                resultCustomer.getStatus().name()
        );

        return CustomerDTOResponseMapper.INSTANCE.toDto(resultCustomer);
    }

    /**
     * Удаляет пользователя по его ID.
     *
     * @param id идентификатор пользователя
     * @return информация о удаленном пользователе
     */
    @Override
    @Transactional
    public CustomerDTOResponse deleteCustomer(UUID id) {
        CustomerDTOResponse customer = getCustomerInfo(id);

        customerRepository.deleteCustomerById(id, Date.valueOf(LocalDate.now()), Status.DELETED.name());

        return customer;
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param customerDTOResponse информация о пользователе
     * @return информация о обновленном пользователе
     */
    @Override
    @Transactional
    public CustomerDTOResponse updateCustomer(CustomerDTOResponse customerDTOResponse) {
        getCustomerInfo(customerDTOResponse.getId());

        CustomerDTOCreateRequest customerDTORequest = customerDTOResponse.getRequest();

        customerRepository.updateCustomerById(customerDTOResponse.getId(),
                customerDTORequest.getFirstName(),
                customerDTORequest.getLastName(),
                customerDTORequest.getEmail(),
                customerDTORequest.getPhone(),
                customerDTORequest.getPassport(),
                Date.valueOf(customerDTORequest.getBirthday()),
                Date.valueOf(LocalDate.now())
        );

        return customerDTOResponse;
    }

    /**
     * Метод для создания пользователя с заданными свойствами.
     *
     * @param customerDTORequest Запрос на создание пользователя.
     * @return Созданный пользователь.
     */
    private Customer getCustomerWithProperties(CustomerDTOCreateRequest customerDTORequest) {
        LocalDate release = LocalDate.now();

        return new Customer(
                UUID.randomUUID(),
                customerDTORequest.getFirstName(),
                customerDTORequest.getLastName(),
                customerDTORequest.getPhone(),
                customerDTORequest.getEmail(),
                customerDTORequest.getPassport(),
                customerDTORequest.getBirthday(),
                Role.CUSTOMER,
                Collections.emptyList(),
                release,
                release,
                Status.ACTIVE
        );
    }
}
