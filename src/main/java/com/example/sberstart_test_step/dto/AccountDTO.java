package com.example.sberstart_test_step.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountDTO {
    @NotBlank
    @JsonProperty("account_id")
    UUID accountId;

    @NotBlank
    @JsonProperty("customer_id")
    UUID customerId;

    @NotBlank
    @JsonProperty("number")
    String number;

    @NotBlank
    @JsonProperty("release")
    LocalDate release;
}
