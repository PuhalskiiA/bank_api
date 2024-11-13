package com.example.sberstart_test_step.dto.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardDTORequest {
    @NotBlank
    @JsonProperty("customer_id")
    UUID customerId;
    @NotBlank
    @JsonProperty("account_id")
    UUID accountId;
    @NotBlank
    @JsonProperty("card_number")
    String number;
    @NotBlank
    @JsonProperty("expired")
    LocalDate expired;
    @NotBlank
    @JsonProperty("cvv")
    String cvv;
}
