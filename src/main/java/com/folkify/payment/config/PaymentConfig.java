package com.folkify.payment.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/** Bật scheduling (cho job timeout) và cung cấp RestTemplate gọi Pay2S. */
@Configuration
@EnableScheduling
public class PaymentConfig {

    @Bean
    public RestTemplate pay2sRestTemplate(RestTemplateBuilder builder) {
        return builder
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(10))
                .build();
    }
}
