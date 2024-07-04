package com.ilevent.ilevent_backend.users.repository;

import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//interface
public interface UserRepository extends JpaRepository <Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByIdAndIsOrganizerTrue(Long id);
    Optional<Users> findByReferralCode(String referralCode);
    Boolean existsByReferralCode(String referralCode);
}
