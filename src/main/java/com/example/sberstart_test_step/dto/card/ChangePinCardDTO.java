package com.example.sberstart_test_step.dto.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChangePinCardDTO {
    @NotBlank
    @JsonProperty("data")
    CardDTORequest cardDTORequest;

    @NotBlank
    @JsonProperty("old_pin")
    String oldPin;

    @NotBlank
    @JsonProperty("new_pin")
    String newPin;
}
