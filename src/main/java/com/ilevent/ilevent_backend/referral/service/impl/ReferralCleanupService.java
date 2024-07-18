package com.ilevent.ilevent_backend.referral.service.impl;

import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class ReferralCleanupService {
    private final UserRepository userRepository;

    public ReferralCleanupService(UserRepository userRepository) {
        this.userRepository = userRepository;
}
    @Scheduled(cron = "0 0 0 * * ?") // Atur cron job untuk berjalan setiap hari pada tengah malam
    @Transactional
    public void updatePromoReferralStatus() {
        Instant threeMonthsAgo = Instant.now().minusSeconds(60L * 60 * 24 * 90); // 3 bulan dalam detik

        List<Users> usersToUpdate = userRepository.findAllByCreatedAtBeforeAndPromoReferralUsedFalse(threeMonthsAgo);

        for (Users user : usersToUpdate) {
            user.setPromoReferralUsed(true);
            userRepository.save(user);
        }
    }
}