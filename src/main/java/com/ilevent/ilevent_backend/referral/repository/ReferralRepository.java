package com.ilevent.ilevent_backend.referral.repository;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ReferralRepository extends JpaRepository<Referral, Long> {
    Optional<Referral> findByReferralCode(String referralCode);
}
