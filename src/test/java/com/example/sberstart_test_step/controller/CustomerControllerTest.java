package com.example.sberstart_test_step.controller;

import com.example.sberstart_test_step.dto.customer.CustomerDTOResponse;
import com.example.sberstart_test_step.dto.customer.CustomerDTOCreateRequest;
import com.example.sberstart_test_step.dto.customer.CustomerDTOFindRequest;
import com.example.sberstart_test_step.exceptions.CustomerException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    final static CustomerDTOCreateRequest CUSTOMER_DTO_CREATE_REQUEST = new CustomerDTOCreateRequest(
            "Aleks",
            "Tikhonov",
            "email@gmail.com",
            "+79124218239",
            "2314123251",
            LocalDate.now()
    );

    final static CustomerDTOCreateRequest CUSTOMER_ALREADY_EXISTS_DTO_CREATE_REQUEST = new CustomerDTOCreateRequest(
            "Мария",
            "Маркова",
            "<EMAIL>",
            "+79001234570",
            "1234567893",
            LocalDate.of(1993, 1, 1)
    );

    final static CustomerDTOResponse CUSTOMER_DTO = new CustomerDTOResponse(UUID.fromString("32414d83-6c4a-4ab2-bfb5-69573ce8b8b2"),
            CUSTOMER_DTO_CREATE_REQUEST);

    final static CustomerDTOResponse CUSTOMER_NOT_FOUND_DTO = new CustomerDTOResponse(UUID.fromString("32414d83-6c4a-4ab2-bfb5-69573ce8a8b2"),
            CUSTOMER_DTO_CREATE_REQUEST);

    final static CustomerDTOFindRequest CUSTOMER_DTO_FIND_REQUEST = new CustomerDTOFindRequest("+79001234572","...");

    final static CustomerDTOFindRequest CUSTOMER_DTO_FIND_REQUEST_CUSTOMER_NOT_FOUND_EXCEPTION = new CustomerDTOFindRequest("88009245465","...");

    final static CustomerDTOCreateRequest CUSTOMER_DTO_CREATE_REQUEST_SIDE = new CustomerDTOCreateRequest(
            "Ольга",
            "Орлова",
            "<EMAIL>",
            "+79001234573",
            "1234567896",
            LocalDate.of(1996, 1, 1)
    );

    final static CustomerDTOResponse CUSTOMER_DTO_SIDE = new CustomerDTOResponse(UUID.fromString("32414d83-6c4a-4ab2-bfb5-69573ce8b8b2"),
            CUSTOMER_DTO_CREATE_REQUEST_SIDE);

//----------------------------------------------------------------------------------------------------------------------
    @Test
    void getInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CUSTOMER_DTO_FIND_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void customerNotFoundExceptionGetInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CUSTOMER_DTO_FIND_REQUEST_CUSTOMER_NOT_FOUND_EXCEPTION)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Пользователь не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    void getInfoById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/32414d83-6c4a-4ab2-bfb5-69573ce8b8b2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CUSTOMER_DTO_SIDE)));
    }

    @Test
    void customerNotFoundExceptionGetInfoById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/32414d83-6c4a-4ab2-b5b5-69573ce8b8b2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Пользователь не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void createCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CUSTOMER_DTO_CREATE_REQUEST))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void customerAlreadyExistExceptionCreateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/customers/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CUSTOMER_ALREADY_EXISTS_DTO_CREATE_REQUEST)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CustomerException))
                .andExpect(result -> assertEquals("Пользователь уже существует", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------

    @Test
    @Transactional
    void deleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/32414d83-6c4a-4ab2-bfb5-69573ce8b8b2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CUSTOMER_DTO_SIDE)));
    }

    @Test
    void customerNotFoundExceptionDeleteCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/32414d83-6c4a-4ab2-bfb5-69573ce8a8b2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Пользователь не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
    @Test
    @Transactional
    void updateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CUSTOMER_DTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(CUSTOMER_DTO)));
    }

    @Test
    void customerNotFoundExceptionUpdateCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/customers")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(CUSTOMER_NOT_FOUND_DTO)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityDataNotFoundException))
                .andExpect(result -> assertEquals("Пользователь не найден", result.getResolvedException().getMessage()));
    }
//----------------------------------------------------------------------------------------------------------------------
}