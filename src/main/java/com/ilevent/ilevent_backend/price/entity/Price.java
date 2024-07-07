package com.ilevent.ilevent_backend.price.entity;

import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "price", schema = "ilevent")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "price_id_gen")
    @SequenceGenerator(name = "price_id_gen", sequenceName = "price_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "price_before_discount")
    private Double priceBeforeDiscount;

    @Column(name = "total_discount")
    private Double totalDiscount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeticket_id")
    private Ticket typeticketId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    protected void onCreate() {
        createdAt = Instant.now();
        updateAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = Instant.now();
    }

    //    sebelum di remove
    @PreRemove
    protected void onDelete () {
        deletedAt = Instant.now();
    }

}