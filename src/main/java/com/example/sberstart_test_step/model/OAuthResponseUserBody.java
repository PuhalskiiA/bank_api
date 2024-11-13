package com.example.sberstart_test_step.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuthResponseUserBody {
    @JsonProperty("login")
    String login;

    @JsonProperty("id")
    String userId;

    @JsonProperty("client_id")
    String clientId;

    @JsonProperty("psuid")
    String psuid;
}
