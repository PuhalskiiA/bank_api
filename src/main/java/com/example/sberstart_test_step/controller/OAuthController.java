package com.example.sberstart_test_step.controller;

import com.example.sberstart_test_step.dto.customer.CustomerDTOResponse;
import com.example.sberstart_test_step.service.customer.CustomerService;
import com.example.sberstart_test_step.service.oauth.OAuthService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/oauth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class OAuthController {
    OAuthService sberIdOAuthServiceImpl;
    CustomerService customerServiceImpl;

    @GetMapping("sber_id")
    public ResponseEntity<CustomerDTOResponse> authenticateSberId(@RequestParam("code") String code,
                                                                  @RequestParam("state") String state) {
        try {
            sberIdOAuthServiceImpl.authenticate(code, state);
            return new ResponseEntity<>(customerServiceImpl.getCustomerInfo(UUID.fromString(state)), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}