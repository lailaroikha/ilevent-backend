package com.ilevent.ilevent_backend.referral.entity;

import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "referral", schema = "ilevent")
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referral_id_gen")
    @SequenceGenerator(name = "referral_id_gen", sequenceName = "referrals_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referred_user_id")
    private Users referredUser;

    @ColumnDefault("10000")
    @Column(name = "points")
    private Integer points;

    @Column(name = "created_at")
    private Instant createdAt;

    @NotNull
    @Column(name = "expired_at", nullable = false)
    private LocalDate expiredAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}