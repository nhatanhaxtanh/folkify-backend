package com.folkify.auth.repository;

import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByAppleSub(String appleSub);

    /** User đang có gói trả phí nhưng đã quá hạn — cần hạ về FREE. */
    List<User> findByPlanNotAndPlanExpiresAtBefore(Plan plan, LocalDateTime time);
}
