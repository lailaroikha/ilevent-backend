package com.ilevent.ilevent_backend.report.entity;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "report", schema = "ilevent")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Events event;

    @ColumnDefault("0")
    @Column(name = "attendees")
    private Integer attendees;

    @ColumnDefault("0")
    @Column(name = "total_revenue", precision = 10, scale = 2)
    private BigDecimal totalRevenue;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Users organizer;

}