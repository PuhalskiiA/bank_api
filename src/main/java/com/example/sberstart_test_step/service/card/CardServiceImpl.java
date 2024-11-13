package com.example.sberstart_test_step.service.card;

import com.example.sberstart_test_step.dto.card.BalanceCardDTOResponse;
import com.example.sberstart_test_step.dto.card.CardDTOResponse;
import com.example.sberstart_test_step.dto.card.ChangeBalanceCardDTO;
import com.example.sberstart_test_step.dto.card.ChangePinCardDTO;
import com.example.sberstart_test_step.dto.card.CreateCardDTORequest;
import com.example.sberstart_test_step.dto.card.SimpleCardDTORequest;
import com.example.sberstart_test_step.exceptions.BalanceException;
import com.example.sberstart_test_step.exceptions.CardRequisitesException;
import com.example.sberstart_test_step.exceptions.EntityDataNotFoundException;
import com.example.sberstart_test_step.mapper.CardDTOResponseMapper;
import com.example.sberstart_test_step.model.base.Account;
import com.example.sberstart_test_step.model.base.Card;
import com.example.sberstart_test_step.repository.AccountRepository;
import com.example.sberstart_test_step.repository.CardRepository;
import com.example.sberstart_test_step.service.card.operation.BalanceOperationHandler;
import com.example.sberstart_test_step.service.card.operation.OperationHandlerRepository;
import com.example.sberstart_test_step.utils.CardStatus;
import com.example.sberstart_test_step.utils.CardValidator;
import com.example.sberstart_test_step.utils.Status;
import com.example.sberstart_test_step.utils.encrypt.EncryptService;
import com.example.sberstart_test_step.utils.number.NumberGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Сервис для работы с картами.
 **/
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardServiceImpl implements CardService {
    @NonFinal
    @Value("${card.number.count}")
    Integer CARD_NUMBER_COUNT;
    @NonFinal
    @Value("${card.alive.years}")
    Integer CARD_ALIVE_YEARS;

    CardRepository cardRepository;
    AccountRepository accountRepository;
    NumberGenerator cardNumberGeneratorImpl;
    OperationHandlerRepository operationHandlerRepository;

    CardValidator validator;
    EncryptService bCryptEncryptServiceImpl;

    /**
     * Метод для создания новой карты.
     *
     * @param createCardDTORequest Запрос на создание карты.
     * @return Информация о созданной карте.
     */
    @Override
    @Transactional
    public CardDTOResponse createCard(CreateCardDTORequest createCardDTORequest) {
        Account account = accountRepository.getAccountById(createCardDTORequest.getAccountId())
                .orElseThrow(() -> new EntityDataNotFoundException("Счет не найден"));

        Card card = getCardWithProperties(createCardDTORequest, account);

        cardRepository.create(card.getId(),
                card.getNumber(),
                card.getCvv(),
                card.getPin(),
                Date.valueOf(card.getRelease()),
                Date.valueOf(card.getExpired()),
                card.getCardStatus().name(),
                card.getBalance(),
                card.getAccount().getId(),
                Date.valueOf(card.getCreated()),
                Date.valueOf(card.getUpdated()),
                card.getStatus().name()
        );

        return CardDTOResponseMapper.INSTANCE.toDto(card);
    }

    /**
     * Метод для получения информации о карте.
     *
     * @param simpleCardDTORequest Запрос на получение информации о карте.
     * @return Информация о карте.
     */
    @Override
    public BalanceCardDTOResponse getBalance(SimpleCardDTORequest simpleCardDTORequest) {
        Card card = getCard(simpleCardDTORequest.getNumber(), simpleCardDTORequest.getCustomerId());

        return new BalanceCardDTOResponse(
                card.getId(),
                card.getNumber(),
                card.getBalance()
        );
    }


    /**
     * Метод для получения информации о карте.
     *
     * @param simpleDTORequest Запрос на получение информации о карте.
     * @return Информация о карте.
     */
    @Override
    public CardDTOResponse getCard(SimpleCardDTORequest simpleDTORequest) {
        return CardDTOResponseMapper.INSTANCE.toDto(cardRepository.getCard(simpleDTORequest.getNumber(), simpleDTORequest.getCustomerId())
                .orElseThrow(() -> new EntityDataNotFoundException("Карта не найдена")));
    }

    /**
     * Метод получает список карт счета по его идентификатору.
     *
     * @param accountId Идентификатор счета.
     * @return Список карт счета.
     */
    @Override
    public List<CardDTOResponse> getCardsByAccount(UUID accountId) {
        return cardRepository.getCardsByAccountId(accountId).stream()
                .map(CardDTOResponseMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод для обновления пин-кода карты.
     *
     * @param changePinCardDTO Запрос на обновление пин-кода.
     * @return Информация о карте.
     */
    @Override
    @Transactional
    public CardDTOResponse updatePin(ChangePinCardDTO changePinCardDTO) {
        CardDTOResponse card = getCard(new SimpleCardDTORequest(
                changePinCardDTO.getCardDTORequest().getCustomerId(),
                changePinCardDTO.getCardDTORequest().getNumber()
        ));

        if (!validator.validatePin(changePinCardDTO.getNewPin())) {
            throw new CardRequisitesException("Невалидный пин");
        }

        cardRepository.updatePin(changePinCardDTO.getCardDTORequest().getNumber(),
                changePinCardDTO.getNewPin(),
                Date.valueOf(LocalDate.now())
        );

        return card;
    }

    /**
     * Метод для обновления баланса карты.
     *
     * @param changeBalanceCardDTO Запрос на обновление баланса.
     * @return Информация о карте.
     */
    @Override
    @Transactional
    public BalanceCardDTOResponse updateBalance(ChangeBalanceCardDTO changeBalanceCardDTO) {
        BalanceCardDTOResponse card = getBalance(new SimpleCardDTORequest(changeBalanceCardDTO.getCardDTORequest().getCustomerId(),
                changeBalanceCardDTO.getCardDTORequest().getNumber()));

        BalanceOperationHandler handler = operationHandlerRepository.getHandler(changeBalanceCardDTO.getKind());
        if (handler == null) {
            throw new BalanceException("Не удалось опознать операцию");
        }

        if (changeBalanceCardDTO.getCount().signum() < 1) {
            throw new BalanceException("Значение должно быть больше нуля");
        }

        BigDecimal result = handler.changeBalance(card.getBalance(), changeBalanceCardDTO.getCount());

        BalanceCardDTOResponse response = new BalanceCardDTOResponse(
                card.getCardId(),
                card.getNumber(),
                result
        );

        cardRepository.updateBalance(response.getNumber(),
                response.getBalance(),
                Date.valueOf(LocalDate.now())
        );

        return response;
    }

    /**
     * Метод для удаления карты.
     *
     * @param simpleCardDTORequest Запрос на удаление карты.
     * @return Информация о карте.
     */
    @Override
    @Transactional
    public SimpleCardDTORequest deleteCard(SimpleCardDTORequest simpleCardDTORequest) {
        getCard(simpleCardDTORequest);

        cardRepository.deleteByNumber(simpleCardDTORequest.getNumber(), Date.valueOf(LocalDate.now()), Status.DELETED.name()
        );

        return simpleCardDTORequest;
    }

    @Override
    public Card getCard(String number, UUID customerId) {
        return cardRepository.getCard(number, customerId)
                .orElseThrow(() -> new EntityDataNotFoundException("Карта не найдена"));
    }

    /**
     * Метод для создания карты с заданными свойствами.
     *
     * @param cardDTORequest Запрос на создание карты.
     * @param account        Сведения об аккаунте.
     * @return Созданная карта.
     */
    private Card getCardWithProperties(CreateCardDTORequest cardDTORequest, Account account) {
        LocalDate release = LocalDate.now();

        return new Card(
                UUID.randomUUID(),
                cardNumberGeneratorImpl.generateNumber(CARD_NUMBER_COUNT),
                bCryptEncryptServiceImpl.encrypt(cardDTORequest.getPin()),
                release,
                release.plusYears(CARD_ALIVE_YEARS),
                Integer.toString(ThreadLocalRandom.current().nextInt(999)),
                new BigDecimal(0),
                account,
                CardStatus.ACTIVE,
                release,
                release,
                Status.ACTIVE

        );
    }
}
