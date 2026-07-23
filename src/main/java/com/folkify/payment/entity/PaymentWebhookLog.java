package com.folkify.payment.entity;

import com.folkify.infrastructure.persistence.BaseEntity;
import com.folkify.payment.enumType.ProcessingStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "payment_webhook_log")
public class PaymentWebhookLog extends BaseEntity {

    @Column(length = 50)
    private String gateway; // "PAYOS"

    @Column(name = "raw_payload", columnDefinition = "text", nullable = false)
    private String rawPayload;

    @Enumerated(EnumType.STRING)
    @Column(name = "processing_status", length = 50)
    private ProcessingStatus processingStatus;

    @Column(name = "error_message", columnDefinition = "text")
    private String errorMessage;

    @Column(name = "client_ip", length = 50)
    private String clientIp;

    public PaymentWebhookLog() {}

    public String getGateway() { return gateway; }
    public void setGateway(String gateway) { this.gateway = gateway; }

    public String getRawPayload() { return rawPayload; }
    public void setRawPayload(String rawPayload) { this.rawPayload = rawPayload; }

    public ProcessingStatus getProcessingStatus() { return processingStatus; }
    public void setProcessingStatus(ProcessingStatus processingStatus) { this.processingStatus = processingStatus; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getClientIp() { return clientIp; }
    public void setClientIp(String clientIp) { this.clientIp = clientIp; }
}
