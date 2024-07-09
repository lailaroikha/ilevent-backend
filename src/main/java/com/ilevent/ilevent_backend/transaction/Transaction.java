package com.ilevent.ilevent_backend.transaction;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transactions", schema = "ilevent")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_id_gen")
    @SequenceGenerator(name = "transactions_id_gen", sequenceName = "transactions_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Events event;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "transaction_date")
    private Instant transactionDate;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Size(max = 20)
    @NotNull
    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "promo_id")
//    private Promo promo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private PointsHistory point;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Integer createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}