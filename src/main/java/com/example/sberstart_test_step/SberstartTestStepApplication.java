package com.example.sberstart_test_step;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class SberstartTestStepApplication {
    public static void main(String[] args) {
        SpringApplication.run(SberstartTestStepApplication.class, args);
    }

    @Bean
    public RestTemplate initialRestTemplate() {
        return new RestTemplate();
    }
}
