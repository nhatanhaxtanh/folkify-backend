package com.folkify.payment.repository;

import com.folkify.payment.entity.PaymentTransaction;
import com.folkify.payment.enumType.TransactionStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {

    boolean existsByGatewayReferenceId(String gatewayReferenceId);

    Optional<PaymentTransaction> findByGatewayReferenceId(String gatewayReferenceId);

    /** Khóa dòng (SELECT ... FOR UPDATE) để chặn webhook trùng xử lý song song. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM PaymentTransaction t WHERE t.gatewayReferenceId = :orderCode")
    Optional<PaymentTransaction> findByGatewayReferenceIdForUpdate(@Param("orderCode") String orderCode);

    List<PaymentTransaction> findByStatusAndCreatedAtBefore(
            TransactionStatus status, LocalDateTime time);
}
