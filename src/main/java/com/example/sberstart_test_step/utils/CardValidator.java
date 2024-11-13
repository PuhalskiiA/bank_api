package com.example.sberstart_test_step.utils;

import com.example.sberstart_test_step.dto.card.CardDTORequest;
import com.example.sberstart_test_step.exceptions.CardRequisitesException;
import com.example.sberstart_test_step.model.base.Card;
import org.springframework.stereotype.Service;

/**
 * Класс реализует сервис валидации данных карты.
 */
@Service
public class CardValidator {
    /**
     * Метод проверяет, является ли введенный PIN корректным.
     *
     * @param pin Введенный PIN.
     * @return Истина, если PIN корректен, иначе ложь.
     */
    public Boolean validatePin(String pin) {
        try {
            int iPin = Integer.parseInt(pin);

            if (iPin <= 0) return false;

            int length = (int) (Math.log10(iPin) + 1);
            return length == 4;
        } catch(NumberFormatException e) {
            throw new CardRequisitesException("Невалидный пин");
        }
    }

    /**
     * Метод проверяет, соответствуют ли данные карты запрашиваемым данным.
     *
     * @param card           Карта.
     * @param cardDTORequest Запрос на получение информации о карте.
     * @return Истина, если данные карты соответствуют запрашиваемым, иначе ложь.
     */
    public Boolean validateRequisites(Card card, CardDTORequest cardDTORequest) {
        return card.getCvv().equals(cardDTORequest.getCvv())
                && card.getNumber().equals(cardDTORequest.getCvv())
                && card.getExpired().isEqual(cardDTORequest.getExpired());
    }
}
