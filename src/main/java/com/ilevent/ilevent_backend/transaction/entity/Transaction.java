package com.ilevent.ilevent_backend.transaction.entity;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.voucherApply.entity.VoucherApply;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "transactions", schema = "ilevent")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Events event;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Size(max = 20)
    @NotNull
    @Column(name = "payment_status", nullable = false, length = 20)
    private String paymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private PointsHistory point;

    @Column(name = "is_points_used")
    private Boolean isPointsUsed;

    @OneToMany(mappedBy = "transactionId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TicketApply> ticketApplies = new HashSet<>();

    @OneToMany(mappedBy = "transactionId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VoucherApply> voucherApplies = new HashSet<>();

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;


    public void setPointsUsed(boolean pointsUsed) {
    }
}