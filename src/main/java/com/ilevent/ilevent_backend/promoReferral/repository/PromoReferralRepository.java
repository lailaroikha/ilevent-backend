package com.ilevent.ilevent_backend.promoReferral.repository;

import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromoReferralRepository extends JpaRepository<PromoReferral, Long> {

    Optional<PromoReferral> findByEventsId(Long eventId);
}
