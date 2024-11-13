package com.example.sberstart_test_step.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerDTOResponse {
    @NotBlank
    @JsonProperty("id")
    UUID id;

    @NotBlank
    @JsonProperty("data")
    CustomerDTOCreateRequest request;
}
