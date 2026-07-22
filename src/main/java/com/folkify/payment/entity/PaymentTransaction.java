package com.folkify.payment.entity;

import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.User;
import com.folkify.infrastructure.persistence.BaseEntity;
import com.folkify.payment.enumType.TransactionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transaction")
public class PaymentTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_transaction_user"))
    private User user;

    /** Gói mà user muốn mua trong giao dịch này. */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_plan", length = 20, nullable = false)
    private Plan targetPlan;

    /** orderId gửi lên Pay2S — dùng để app poll trạng thái. Duy nhất. */
    @Column(name = "gateway_reference_id", length = 100, nullable = false, unique = true)
    private String gatewayReferenceId;

    /** Mã giao dịch phía ngân hàng (do webhook trả về). */
    @Column(name = "bank_transaction_code", length = 100)
    private String bankTransactionCode;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    /** Nội dung chuyển khoản — khóa để đối soát với webhook. */
    @Column(name = "transfer_content", columnDefinition = "text", nullable = false)
    private String transferContent;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private TransactionStatus status;

    public PaymentTransaction() {}

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Plan getTargetPlan() { return targetPlan; }
    public void setTargetPlan(Plan targetPlan) { this.targetPlan = targetPlan; }

    public String getGatewayReferenceId() { return gatewayReferenceId; }
    public void setGatewayReferenceId(String gatewayReferenceId) { this.gatewayReferenceId = gatewayReferenceId; }

    public String getBankTransactionCode() { return bankTransactionCode; }
    public void setBankTransactionCode(String bankTransactionCode) { this.bankTransactionCode = bankTransactionCode; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getTransferContent() { return transferContent; }
    public void setTransferContent(String transferContent) { this.transferContent = transferContent; }

    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }

    public TransactionStatus getStatus() { return status; }
    public void setStatus(TransactionStatus status) { this.status = status; }
}
