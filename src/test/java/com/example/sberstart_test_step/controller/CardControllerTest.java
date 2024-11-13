package com.example.sberstart_test_step.controller;

import com.example.sberstart_test_step.dto.card.BalanceCardDTOResponse;
import com.example.sberstart_test_step.dto.card.CardDTORequest;
import com.example.sberstart_test_step.dto.card.CardDTOResponse;
import com.example.sberstart_test_step.dto.card.ChangeBalanceCardDTO;
import com.example.sberstart_test_step.dto.card.ChangePinCardDTO;
import com.example.sberstart_test_step.dto.card.CreateCardDTORequest;
import com.example.sberstart_test_step.dto.card.SimpleCardDTORequest;
import com.example.sberstart_test_step.exceptions.EntityDataNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class CardControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    final static CreateCardDTORequest CREATE_CARD_DTO_REQUEST = new CreateCardDTORequest(
            UUID.fromString("98d8463a-2e2f-4080-a5e5-1dab740af567"),
            UUID.fromString("b2fa630c-f632-4428-9e48-85312ff882a5"),
            "141"
    );

    final static CreateCardDTORequest ACCOUNT_NOT_FOUND_CREATE_CARD_DTO_REQUEST = new CreateCardDTORequest(
            UUID.fromString("98d8463a-2e2f-4080-a5e5-1dab740af567"),
            UUID.fromString("b2fa630c-f632-4428-9e48-85312ff892a5"),
            "141"
    );

    final static SimpleCardDTORequest SIMPLE_CARD_DTO_REQUEST = new SimpleCardDTORequest(
            UUID.fromString("98d8463a-2e2f-4080-a5e5-1dab740af567"),
            "1234567890123457"
    );

    final static SimpleCardDTORequest CARD_NOT_FOUND_SIMPLE_CARD_DTO_REQUEST = new SimpleCardDTORequest(
            UUID.fromString("98d8463a-2e2f-4080-a5e5-1dab740af567"),
            "12345670123457"
    );

    final static CardDTOResponse CARD_DTO_RESPONSE = new CardDTOResponse(
            UUID.fromString("a06c3d04-9bb2-40ac-8897-3cd34cbea71a"),
            "1234567890123457",
            LocalDate.of(2025, 1, 1),
            "Василий"
    );

    final static BalanceCardDTOResponse BALANCE_CARD_DTO_RESPONSE = new BalanceCardDTOResponse(
            UUID.fromString("a06c3d04-9bb2-40ac-8897-3cd34cbea71a"),
            "1234567890123457",
            new BigDecimal(100000)
    );

    final static CardDTORequest CARD_DTO_REQUEST = new CardDTORequest(
            UUID.fromString("98d8463a-2e2f-4080-a5e5-1dab740af567"),
            UUID.fromString("b2fa630c-f632-4428-9e48-85312ff882a5"),
            "1234567890123457",
            LocalDate.of(2025, 1, 1),
            "123"
    );

    final static CardDTORequest CARD_NOT_FOUND_DTO_REQUEST = new CardDTORequest(
            UUID.fromString("98d8463a-2e2f-4080-a5e5-1dab740af567"),
            UUID.fromString("b2fa630c-f632-4428-9e48-85312ff882a5"),
            "1234523457",
            LocalDate.of(2025, 1, 1),
            "123"
    );

    final static ChangeBalanceCardDTO CHANGE_BALANCE_CARD_DTO = new ChangeBalanceCardDTO(
            CARD_DTO_REQUEST,
            "Increase",
            new BigDecimal(100000)
    );

    final static ChangeBalanceCardDTO CARD_NOTFOUND_CHANGE_BALANCE_DTO = new ChangeBalanceCardDTO(
            CARD_NOT_FOUND_DTO_REQUEST,
            "Increase",
            new BigDecimal(100000)
    );

    final static ChangePinCardDTO CHANGE_PIN_CARD_DTO = new ChangePinCardDTO(
            CARD_DTO_REQUEST,
            "123",
            "1234"
    );

    final static ChangePinCardDTO CARD_NOT_FOUND_CHANGE_PIN_DTO = new ChangePinCardDTO(
            CARD_NOT_FOUND_DTO_REQUEST,
            "123",
            "1234"
    );

//----------------------------------------------------------------------------------------------------------------------
    @Test
    void getCardsByAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cards/account/b2fa630c-f632-4428-9e48-85312ff882a5"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void createCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cards/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CREATE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void accountNotFoundExceptionCreateCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cards/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(ACCOUNT_NOT_FOUND_CREATE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Счет не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    void getBalance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cards/balance")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(SIMPLE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void cardNotFoundExceptionGetBalance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cards/balance")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CARD_NOT_FOUND_SIMPLE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Карта не найдена", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    void getCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cards")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(SIMPLE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CARD_DTO_RESPONSE)));
    }

    @Test
    void cardNotFoundExceptionGetCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/cards")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CARD_NOT_FOUND_SIMPLE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Карта не найдена", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void changeBalance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/cards/balance")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CHANGE_BALANCE_CARD_DTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(BALANCE_CARD_DTO_RESPONSE)));
    }

    @Test
    void cardNotFoundExceptionChangeBalance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/cards/balance")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CARD_NOTFOUND_CHANGE_BALANCE_DTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Карта не найдена", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void changePin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/cards/pin")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CHANGE_PIN_CARD_DTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CARD_DTO_RESPONSE)));
    }

    @Test
    void cardNotFoundExceptionChangePin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/cards/pin")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CARD_NOT_FOUND_CHANGE_PIN_DTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Карта не найдена", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void deleteCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cards")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(SIMPLE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(SIMPLE_CARD_DTO_REQUEST)));
    }

    @Test
    void cardNotFoundExceptionDeleteCard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/cards")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CARD_NOT_FOUND_SIMPLE_CARD_DTO_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Карта не найдена", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
}