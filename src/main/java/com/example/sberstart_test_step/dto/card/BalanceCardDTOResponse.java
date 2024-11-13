package com.example.sberstart_test_step.dto.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BalanceCardDTOResponse {
    @NotBlank
    @JsonProperty("card_id")
    UUID cardId;

    @NotBlank
    @JsonProperty("number")
    String number;

    @NotBlank
    @JsonProperty("balance")
    BigDecimal balance;
}
