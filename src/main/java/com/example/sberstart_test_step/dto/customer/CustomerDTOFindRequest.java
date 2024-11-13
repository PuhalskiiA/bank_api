package com.example.sberstart_test_step.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerDTOFindRequest {
    @NotBlank
    @JsonProperty("phone")
    String phone;

    @NotBlank
    @JsonProperty("email")
    String email;
}
