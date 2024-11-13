package com.example.sberstart_test_step.service.oauth;

import com.example.sberstart_test_step.model.OAuthResponseTokenBody;
import com.example.sberstart_test_step.model.OAuthResponseUserBody;
import com.example.sberstart_test_step.service.customer.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Objects;

//TODO Доделать по возможности
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class YandexIdOAuthServiceImpl implements OAuthService {
    @Value("${client.id}")
    String clientId;
    @Value("${client.secret}")
    String clientSecret;

    final CustomerService customerServiceImpl;
    final RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> authenticate(String code, String state) {
        ResponseEntity<OAuthResponseTokenBody> tokenResponseEntityBody = getTokens(code);
        ResponseEntity<OAuthResponseUserBody> userData = getUserData(tokenResponseEntityBody);

//        customerServiceImpl.createCustomer();

        return null;
    }

    @Override
    public String getCodeURL(String userId) {
        String url = "https://oauth.yandex.ru/authorize";
        String responseType = "code";

        return url + "?response_type=" + responseType +
                "&client_id=" + clientId +
                "&state=" + userId;
    }

    private ResponseEntity<OAuthResponseTokenBody> getTokens(String code) {
        MultiValuedMap<String, String> requestBody = new ArrayListValuedHashMap<>();

        requestBody.put("grant_type", "authorization_code");
        requestBody.put("code", code);
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);

        String credentials = getCredentials();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + credentials);

        HttpEntity<MultiValuedMap<String, String>> tokenResponseEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity("https://oauth.yandex.tu/token",
                tokenResponseEntity, OAuthResponseTokenBody.class);
    }

    private ResponseEntity<OAuthResponseUserBody> getUserData(ResponseEntity<OAuthResponseTokenBody> tokenResponse) {
        MultiValuedMap<String, String> requestBody = new ArrayListValuedHashMap<>();

        HttpHeaders headers = new HttpHeaders();

        requestBody.put("format", "json");
        headers.add("Authorization", "Oauth "
                + Objects.requireNonNull(tokenResponse.getBody()).getAccessToken());

        HttpEntity<MultiValuedMap<String, String>> userDataRequestEntity = new HttpEntity<>(requestBody, headers);

        return restTemplate.postForEntity("https://login.yandex.ru/info?", userDataRequestEntity,
                OAuthResponseUserBody.class);
    }

    private String getCredentials() {
        return Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());
    }
}
