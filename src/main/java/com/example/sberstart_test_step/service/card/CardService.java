package com.example.sberstart_test_step.service.card;

import com.example.sberstart_test_step.dto.card.BalanceCardDTOResponse;
import com.example.sberstart_test_step.dto.card.CardDTOResponse;
import com.example.sberstart_test_step.dto.card.ChangeBalanceCardDTO;
import com.example.sberstart_test_step.dto.card.ChangePinCardDTO;
import com.example.sberstart_test_step.dto.card.CreateCardDTORequest;
import com.example.sberstart_test_step.dto.card.SimpleCardDTORequest;
import com.example.sberstart_test_step.model.base.Card;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс определяет сервисы работы с картами.
 */
public interface CardService {
    /**
     * Метод создает новую карту.
     *
     * @param createCardDTORequest Запрос на создание карты.
     * @return Информация о карте.
     */
    CardDTOResponse createCard(CreateCardDTORequest createCardDTORequest);

    /**
     * Метод получает информацию о карте.
     *
     * @param cardDTORequest Запрос на получение информации о карте.
     * @return Информация о карте.
     */
    BalanceCardDTOResponse getBalance(SimpleCardDTORequest cardDTORequest);

    /**
     * Метод получает информацию о карте.
     *
     * @param simpleDTORequest запрос на получение информации о карте.
     * @return Информация о карте.
     */
    CardDTOResponse getCard(SimpleCardDTORequest simpleDTORequest);

    /**
     * Метод получает список карт счета по его идентификатору.
     *
     * @param accountId Идентификатор счета.
     * @return Список карт счета.
     */
    List<CardDTOResponse> getCardsByAccount(UUID accountId);

    /**
     * Метод обновляет пин-код карты.
     *
     * @param changePinCardDTO Запрос на обновление пин-кода.
     * @return Информация о карте.
     */
    CardDTOResponse updatePin(ChangePinCardDTO changePinCardDTO);

    /**
     * Метод обновляет баланс карты.
     *
     * @param changeBalanceCardDTO Запрос на обновление баланса.
     * @return Информация о карте.
     */
    BalanceCardDTOResponse updateBalance(ChangeBalanceCardDTO changeBalanceCardDTO);

    /**
     * Метод удаляет карту.
     *
     * @param request Запрос на удаление карты.
     * @return Информация о карте.
     */
    SimpleCardDTORequest deleteCard(SimpleCardDTORequest request);

    /**
     * Метод предназначен для внутреннего использования и получает карту.
     *
     * @param number     Номер карты.
     * @param customerId Идентификатор клиента.
     * @return Информация о карте.
     */
    Card getCard(String number, UUID customerId);
}
