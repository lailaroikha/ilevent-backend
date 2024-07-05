package com.ilevent.ilevent_backend.referral.repository;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {
    Referral findReferralByUserId(Long userId);
}
