package com.folkify.payment.config;

import com.folkify.auth.entity.Plan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

/** Cấu hình cổng thanh toán Pay2S, bind từ prefix "pay2s" trong application.yml. */
@Component
@ConfigurationProperties(prefix = "pay2s")
public class Pay2sProperties {

    /** Endpoint tạo link của Pay2S. */
    private String apiUrl = "https://payment.pay2s.vn/v1/gateway/api/create";
    private String partnerCode;
    private String partnerName = "Folkify";
    private String accessKey;
    private String secretKey;
    /** URL Pay2S gọi lại khi có tiền về (webhook). */
    private String ipnUrl;
    /** URL Pay2S redirect trình duyệt sau khi thanh toán. */
    private String redirectUrl;

    /** Tài khoản ngân hàng nhận tiền của Folkify. */
    private Bank bank = new Bank();

    /** Bảng giá theo gói (VND). Ví dụ: BASIC=99000, PRO=199000. */
    private Map<Plan, Long> planPrices = new EnumMap<>(Plan.class);

    /** Bật kiểm tra chữ ký webhook (so header Authorization với HMAC của raw body). */
    private boolean verifyWebhookSignature = false;

    public static class Bank {
        private String accountNumber;
        private String bankId;        // mã ngân hàng theo chuẩn Pay2S (VD: "MB", "VCB"...)
        private String accountHolder;

        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public String getBankId() { return bankId; }
        public void setBankId(String bankId) { this.bankId = bankId; }
        public String getAccountHolder() { return accountHolder; }
        public void setAccountHolder(String accountHolder) { this.accountHolder = accountHolder; }
    }

    public String getApiUrl() { return apiUrl; }
    public void setApiUrl(String apiUrl) { this.apiUrl = apiUrl; }
    public String getPartnerCode() { return partnerCode; }
    public void setPartnerCode(String partnerCode) { this.partnerCode = partnerCode; }
    public String getPartnerName() { return partnerName; }
    public void setPartnerName(String partnerName) { this.partnerName = partnerName; }
    public String getAccessKey() { return accessKey; }
    public void setAccessKey(String accessKey) { this.accessKey = accessKey; }
    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
    public String getIpnUrl() { return ipnUrl; }
    public void setIpnUrl(String ipnUrl) { this.ipnUrl = ipnUrl; }
    public String getRedirectUrl() { return redirectUrl; }
    public void setRedirectUrl(String redirectUrl) { this.redirectUrl = redirectUrl; }
    public Bank getBank() { return bank; }
    public void setBank(Bank bank) { this.bank = bank; }
    public Map<Plan, Long> getPlanPrices() { return planPrices; }
    public void setPlanPrices(Map<Plan, Long> planPrices) { this.planPrices = planPrices; }
    public boolean isVerifyWebhookSignature() { return verifyWebhookSignature; }
    public void setVerifyWebhookSignature(boolean verifyWebhookSignature) { this.verifyWebhookSignature = verifyWebhookSignature; }
}
