package com.example.sberstart_test_step.dto.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerDTOCreateRequest {
    @NotBlank
    @JsonProperty("first_name")
    String firstName;

    @NotBlank
    @JsonProperty("last_name")
    String lastName;

    @NotBlank
    @JsonProperty("email")
    String email;

    @NotBlank
    @JsonProperty("phone")
    String phone;

    @NotBlank
    @JsonProperty("passport")
    String passport;

    @NotBlank
    @JsonProperty("birthday")
    LocalDate birthday;
}
