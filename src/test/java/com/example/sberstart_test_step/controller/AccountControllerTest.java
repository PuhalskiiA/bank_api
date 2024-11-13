package com.example.sberstart_test_step.controller;

import com.example.sberstart_test_step.dto.AccountDTO;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    final static AccountDTO ACCOUNT_DTO = new AccountDTO(
            UUID.fromString("b2fa630c-f632-4428-9e48-85312ff882a5"),
            UUID.fromString("98d8463a-2e2f-4080-a5e5-1dab740af567"),
            "1234567890123457",
            LocalDate.of(2022, 1, 1)
    );

//----------------------------------------------------------------------------------------------------------------------
    @Test
    void getAccountsByCustomerId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/customer/98d8463a-2e2f-4080-a5e5-1dab740af567"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//----------------------------------------------------------------------------------------------------------------------
    @Test
    void getAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/b2fa630c-f632-4428-9e48-85312ff882a5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ACCOUNT_DTO)));
    }

    @Test
    void accountNotFoundExceptionGetAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/b2fa630c-f632-4428-9e48-85362ff882a5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Счет не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void createAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/98d8463a-2e2f-4080-a5e5-1dab740af567"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void customerNotFoundExceptionCreateAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accounts/98d8463a-2e2f-4080-a5e5-1dab740af467"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Пользователь не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void deleteAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/b2fa630c-f632-4428-9e48-85312ff882a5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(ACCOUNT_DTO)));
    }

    @Test
    void accountNotFoundExceptionDeleteAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/b2fa630c-f632-4458-9e48-85312ff882a5"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Счет не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
}