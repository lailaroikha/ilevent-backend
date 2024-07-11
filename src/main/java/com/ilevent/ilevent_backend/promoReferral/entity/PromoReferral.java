package com.ilevent.ilevent_backend.promoReferral.entity;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "promo_referral", schema = "ilevent")
public class PromoReferral {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promo_referral_id_gen")
    @SequenceGenerator(name = "promo_referral_id_gen", sequenceName = "promo_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_id")
    private Events eventsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users usersID;

    @Column(name = "promo_value_discount")
    private Integer promoValueDiscount;

    @Column(name = "start")
    private Instant start;

    @Column(name = "\"end\"")
    private Instant end;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

}