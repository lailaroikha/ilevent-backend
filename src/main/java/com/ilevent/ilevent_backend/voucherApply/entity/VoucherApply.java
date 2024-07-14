package com.ilevent.ilevent_backend.voucherApply.entity;

import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.transaction.entity.Transaction;
import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "voucher_apply", schema = "ilevent")
public class VoucherApply {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private Transaction transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promo_referral_id")
    private PromoReferral promoReferralId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_id")
    private Voucher voucherId;

    @NotNull
    @Column(name = "quantity", nullable = false)  // Menambahkan field quantity
    private Integer quantity;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}