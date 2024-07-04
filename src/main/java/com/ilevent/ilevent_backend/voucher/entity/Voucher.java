package com.ilevent.ilevent_backend.voucher.entity;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "voucher", schema = "ilevent")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voucher_id_gen")
    @SequenceGenerator(name = "voucher_id_gen", sequenceName = "voucher_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Events event;

    @Size(max = 20)
    @NotNull
    @Column(name = "discount_code", nullable = false, length = 20)
    private String discountCode;

    @NotNull
    @Column(name = "discount_percentage", nullable = false)
    private Integer discountPercentage;

    @NotNull
    @Column(name = "max_uses", nullable = false)
    private Integer maxUses;

    @ColumnDefault("0")
    @Column(name = "used")
    private Integer used;

    @Column(name = "expired_at")
    private Instant expiredAt;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

}