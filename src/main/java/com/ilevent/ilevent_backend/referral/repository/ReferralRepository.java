package com.ilevent.ilevent_backend.referral.repository;

import com.ilevent.ilevent_backend.referral.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReferralRepository extends JpaRepository<Referral, Long> {

}
