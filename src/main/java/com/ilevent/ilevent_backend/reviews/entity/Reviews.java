package com.ilevent.ilevent_backend.reviews.entity;

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
@Table(name = "reviews", schema = "ilevent")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_id_gen")
    @SequenceGenerator(name = "reviews_id_gen", sequenceName = "reviews_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Events event;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "review", length = Integer.MAX_VALUE)
    private String review;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @NotNull
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

}