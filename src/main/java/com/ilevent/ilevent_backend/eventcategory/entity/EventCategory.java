package com.ilevent.ilevent_backend.eventcategory.entity;

import com.ilevent.ilevent_backend.events.entity.Events;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "event_categories", schema = "ilevent")
public class EventCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_categories_id_gen")
    @SequenceGenerator(name = "event_categories_id_gen", sequenceName = "event_categories_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Events event;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "deleted_at")
    private Integer deletedAt;
    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

/*
 TODO [Reverse Engineering] create field to map the 'category' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "category", columnDefinition = "event_category_type not null")
    private Object category;
*/
}