package com.ilevent.ilevent_backend.event.entity;


import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "event", schema = "ilevent")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private Users organizer;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Instant date;

    @Column(nullable = false)
    private Instant time;

    @Column(nullable = false, length = 255)
    private String location;

    @Column(columnDefinition = "DECIMAL(10, 2) DEFAULT 0.00")
    private BigDecimal price;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isPaid;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedAt;



}
