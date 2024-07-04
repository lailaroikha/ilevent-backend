package com.ilevent.ilevent_backend.pointsHistory.entity;

import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "points_history", schema = "ilevent")
public class PointsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "points_history_id_gen")
    @SequenceGenerator(name = "points_history_id_gen", sequenceName = "points_history_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @NotNull
    @Column(name = "points", nullable = false)
    private Integer points;

    @Size(max = 20)
    @NotNull
    @Column(name = "type", nullable = false, length = 20)
    private String type;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "deleted_at")
    private Integer deletedAt;

    @NotNull
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

}