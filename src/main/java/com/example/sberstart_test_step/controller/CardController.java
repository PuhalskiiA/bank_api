package com.example.sberstart_test_step.controller;

import com.example.sberstart_test_step.dto.card.BalanceCardDTOResponse;
import com.example.sberstart_test_step.dto.card.CardDTOResponse;
import com.example.sberstart_test_step.dto.card.ChangeBalanceCardDTO;
import com.example.sberstart_test_step.dto.card.ChangePinCardDTO;
import com.example.sberstart_test_step.dto.card.CreateCardDTORequest;
import com.example.sberstart_test_step.dto.card.SimpleCardDTORequest;
import com.example.sberstart_test_step.service.card.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Класс реализует контроллер для работы с картами.
 */
@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Card controller", description = "Контроллер для работы с картами")
public class CardController {
    CardService cardServiceImpl;

    /**
     * Метод получает список карт по идентификатору счета.
     *
     * @param accountId Данные счета.
     * @return Список карт счета.
     */
    @Operation(
            summary = "Получить список карт по идентификатору счета",
            description = "Получить список карт по идентификатору счета")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список карт счета"),
    })
    @GetMapping(value = "/account/{account_id}")
    public List<CardDTOResponse> getCardsByAccount(@PathVariable("account_id") UUID accountId) {
        return cardServiceImpl.getCardsByAccount(accountId);
    }

    /**
     * Метод создает новую карту.
     *
     * @param createCardDTORequest Запрос на создание карты.
     * @return Ответ на создание карты.
     */
    @Operation(
            summary = "Создать новую карту",
            description = "Создает новую карту")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Новая карта создана"),
            @ApiResponse(responseCode = "404", description = "Счет не найден")
    })
    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDTOResponse createCard(@RequestBody CreateCardDTORequest createCardDTORequest) {
        return cardServiceImpl.createCard(createCardDTORequest);
    }

    /**
     * Метод получает информацию о карте по её идентификатору.
     *
     * @param simpleCardDTORequest Запрос на получение информации о карте.
     * @return Информация о карте.
     */
    @Operation(
            summary = "Получить информацию о карте по её идентификатору",
            description = "Возвращает информацию о карте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о карте"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @PostMapping(value = "/balance", consumes = "application/json")
    public BalanceCardDTOResponse getBalance(@RequestBody SimpleCardDTORequest simpleCardDTORequest) {
        return cardServiceImpl.getBalance(simpleCardDTORequest);
    }

    /**
     * Метод получает информацию о карте по её идентификатору.
     *
     * @param simpleCardDTORequest Запрос на получение информации о карте.
     * @return Информация о карте.
     */
    @Operation(
            summary = "Получить информацию о карте по её идентификатору",
            description = "Возвращает информацию о карте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация о карте"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @PostMapping(consumes = "application/json")
    public CardDTOResponse getCard(@RequestBody SimpleCardDTORequest simpleCardDTORequest) {
        return cardServiceImpl.getCard(simpleCardDTORequest);
    }

    /**
     * Метод обновляет баланс карты.
     *
     * @param changeBalanceCardDTO Запрос на обновление баланса.
     * @return Ответ на обновление баланса.
     */
    @Operation(
            summary = "Изменить баланс карты",
            description = "Обновляет баланс карты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Баланс карты обновлен"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @PatchMapping(value = "/balance", consumes = "application/json")
    public BalanceCardDTOResponse changeBalance(@RequestBody ChangeBalanceCardDTO changeBalanceCardDTO) {
        return cardServiceImpl.updateBalance(changeBalanceCardDTO);
    }

    /**
     * Метод обновляет пин-код карты.
     *
     * @param changePinCardDTO Запрос на обновление пин-кода.
     * @return Ответ на обновление пин-кода.
     */
    @Operation(
            summary = "Обновить пин-код карты",
            description = "Обновляет пин-код карты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пин-код карты обновлен"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @PatchMapping(value = "/pin", consumes = "application/json")
    public CardDTOResponse changePin(@RequestBody ChangePinCardDTO changePinCardDTO) {
        return cardServiceImpl.updatePin(changePinCardDTO);
    }

    /**
     * Метод удаляет карту.
     *
     * @param simpleCardDTORequest Запрос на удаление карты.
     * @return Ответ на удаление карты.
     */
    @Operation(
            summary = "Удалить карту",
            description = "Обновляет пин-код карты")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Карта успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
    })
    @DeleteMapping(consumes = "application/json")
    public SimpleCardDTORequest deleteCard(@RequestBody SimpleCardDTORequest simpleCardDTORequest) {
        return cardServiceImpl.deleteCard(simpleCardDTORequest);
    }
}
