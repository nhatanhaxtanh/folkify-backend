package com.folkify.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

/** Một dòng biến động số dư trong webhook Pay2S. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Pay2sTransaction(
        String id,
        String gateway,
        String transactionDate,
        String transactionNumber,
        String accountNumber,
        String content,
        String transferType,
        BigDecimal transferAmount,
        String checksum) {}
