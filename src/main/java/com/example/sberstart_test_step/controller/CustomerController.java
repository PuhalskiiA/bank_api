package com.example.sberstart_test_step.controller;

import com.example.sberstart_test_step.dto.customer.CustomerDTOResponse;
import com.example.sberstart_test_step.dto.customer.CustomerDTOCreateRequest;
import com.example.sberstart_test_step.dto.customer.CustomerDTOFindRequest;
import com.example.sberstart_test_step.service.customer.CustomerService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/customers")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Tag(name = "Customer controller", description = "Контроллер для работы с клиентами")
public class CustomerController {
    CustomerService customerServiceImpl;

    /**
     * Метод получает информацию о клиенте по его идентификатору.
     *
     * @param customerDTOFindRequest Данные для получения информации о клиенте.
     * @return Информация о клиенте.
     */
    @Operation(
            summary = "Получить информацию о клиенте по его номеру телефона",
            description = "Возвращает информацию о клиенте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о клиенте"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PostMapping(consumes = "application/json")
    public CustomerDTOResponse getInfo(@RequestBody CustomerDTOFindRequest customerDTOFindRequest) {
        return customerServiceImpl.getCustomerInfo(customerDTOFindRequest.getPhone());
    }

    /**
     * Метод получает информацию о клиенте по его идентификатору.
     *
     * @param customerId Идентификатор клиента.
     * @return Информация о клиенте.
     */
    @Operation(
            summary = "Получить информацию о клиенте по его идентификатору",
            description = "Возвращает информацию о клиенте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о клиенте"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @GetMapping(value = "/{customer_id}")
    public CustomerDTOResponse getInfo(@PathVariable("customer_id") UUID customerId) {
        return customerServiceImpl.getCustomerInfo(customerId);
    }

    /**
     * Метод создает нового клиента.
     *
     * @param customerDTO Данные для создания клиента.
     * @return Информация о созданном клиенте.
     */
    @Operation(
            summary = "Создать нового клиента",
            description = "Создает нового клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Новый клиент создан"),
            @ApiResponse(responseCode = "400", description = "Пользователь уже существует")
    })
    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTOResponse createCustomer(@RequestBody CustomerDTOCreateRequest customerDTO) {
        return customerServiceImpl.createCustomer(customerDTO);
    }

    /**
     * Метод удаляет клиента.
     *
     * @param customerId Информация о клиенте.
     * @return Информация об удаленном клиенте.
     */
    @Operation(
            summary = "Удалить клиента",
            description = "Удаляет клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клиент успешно удален"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @DeleteMapping(value = "/{customer_id}")
    public CustomerDTOResponse deleteCustomer(@PathVariable("customer_id") UUID customerId) {
        return customerServiceImpl.deleteCustomer(customerId);
    }

    /**
     * Метод обновляет информацию о клиенте.
     *
     * @param customerDTOResponse Информация о клиенте.
     * @return Информация об обновленном клиенте.
     */
    @Operation(
            summary = "Обновить информацию о клиенте",
            description = "Обновляет информацию о клиенте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Клиент успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Клиент не найден")
    })
    @PutMapping(consumes = "application/json")
    public CustomerDTOResponse updateCustomer(@RequestBody CustomerDTOResponse customerDTOResponse) {
        return customerServiceImpl.updateCustomer(customerDTOResponse);
    }
}
