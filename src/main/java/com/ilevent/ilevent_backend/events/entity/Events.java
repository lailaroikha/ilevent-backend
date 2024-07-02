package com.ilevent.ilevent_backend.events.entity;


import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "events", schema = "ilevent")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "organizer_id", nullable = false)
    private Users organizer;

    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(nullable = false)
    private Instant date;

    @NotNull
    @Column(nullable = false)
    private Instant time;

    @NotNull
    @Column(nullable = false)
    private String location;

    @NotNull
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @NotNull
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @NotNull
    @Column(nullable = false)
    private String image;

    @ColumnDefault("false")
    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "deleted_at")
    private Instant deletedAt;

}
