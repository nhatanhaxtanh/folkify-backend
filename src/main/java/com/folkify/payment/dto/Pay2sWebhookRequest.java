package com.folkify.payment.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Pay2sWebhookRequest(List<Pay2sTransaction> transactions) {}
