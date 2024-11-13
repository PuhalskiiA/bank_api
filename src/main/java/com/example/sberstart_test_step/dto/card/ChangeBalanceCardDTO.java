package com.example.sberstart_test_step.dto.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class ChangeBalanceCardDTO {
    @NotBlank
    @JsonProperty("data")
    CardDTORequest cardDTORequest;

    @NotBlank
    @JsonProperty("operation")
    String kind;

    @NotBlank
    @JsonProperty("count")
    BigDecimal count;
}
