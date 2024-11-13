package com.example.sberstart_test_step.service.oauth;

import org.springframework.http.ResponseEntity;

public interface OAuthService {
    ResponseEntity<String> authenticate(String code, String state);

    String getCodeURL(String userId);
}
