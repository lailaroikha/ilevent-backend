package com.ilevent.ilevent_backend.ticket.entity;

import com.ilevent.ilevent_backend.events.entity.Events;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tickets", schema = "ilevent")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tickets_id_gen")
    @SequenceGenerator(name = "tickets_id_gen", sequenceName = "type_ticket_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Events eventId;

    @Size(max = 50)
    @NotNull
    @Column(name = "name_ticket", nullable = false, length = 50)
    private String nameTicket;

    @NotNull
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(name = "price_before_discount")
    private Double priceBeforeDiscount;

    @Column(name = "total_discount")
    private Double totalDiscount;

    @Column(name = "price_after_discount")
    private Double priceAfterDiscount;

    @Column(name="booked")
    private Integer booked = 0;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}