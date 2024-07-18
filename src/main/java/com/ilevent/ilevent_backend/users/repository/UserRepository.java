package com.ilevent.ilevent_backend.users.repository;

import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
//interface
public interface UserRepository extends JpaRepository <Users, Long> {
    Optional<Users> findByEmail(String email);
    Users findByUsername(String username);
    Optional<Users> findByReferralCode(String referralCode);

    List<Users> findAllByCreatedAtBeforeAndPromoReferralUsedFalse(Instant date); // Metode baru untuk mencari pengguna yang berusia lebih dari 3 bulan dan belum menggunakan promo referral
}
