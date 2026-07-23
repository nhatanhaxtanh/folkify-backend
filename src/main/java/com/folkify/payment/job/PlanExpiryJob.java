package com.folkify.payment.job;

import com.folkify.auth.entity.Plan;
import com.folkify.auth.entity.User;
import com.folkify.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/** Hạ các user có gói trả phí đã hết hạn về FREE. */
@Component
public class PlanExpiryJob {

    private static final Logger log = LoggerFactory.getLogger(PlanExpiryJob.class);

    private final UserRepository userRepository;

    public PlanExpiryJob(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 */15 * * * *") // mỗi 15 phút
    @Transactional
    public void scanAndDowngradeExpiredPlans() {
        LocalDateTime now = LocalDateTime.now();

        List<User> expired =
                userRepository.findByPlanNotAndPlanExpiresAtBefore(Plan.FREE, now);

        if (expired.isEmpty()) {
            return;
        }

        log.info("Phát hiện {} gói hết hạn. Hạ về FREE...", expired.size());
        for (User user : expired) {
            user.setPlan(Plan.FREE);
            user.setPlanExpiresAt(null);
        }
        userRepository.saveAll(expired);
    }
}
