package com.folkify.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import vn.payos.PayOS;

/** Bật scheduling (cho job timeout) và cung cấp client PayOS. */
@Configuration
@EnableScheduling
public class PaymentConfig {

    @Bean
    public PayOS payOS(PayOsProperties props) {
        return new PayOS(props.getClientId(), props.getApiKey(), props.getChecksumKey());
    }
}
