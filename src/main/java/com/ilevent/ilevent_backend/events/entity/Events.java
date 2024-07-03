package com.ilevent.ilevent_backend.events.entity;

import com.ilevent.ilevent_backend.users.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private LocalDate date;

    @NotNull
    @Column(nullable = false)
    private LocalTime time;


    @Column(nullable = false)
    private String location;

    @NotNull
    @Column(nullable = false, name="created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @NotNull
    @Column(nullable = false, name="updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @NotNull
    @Column(name="image",nullable = false)
    private String image;

    @ColumnDefault("false")
    @Column(name = "is_freeEvent")
    private Boolean isFreeEvent;

    @Column(name = "deleted_at")
    private Instant deletedAt;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "event_categories_id")
//    private EventCategory eventCategoriesId;


    //    update data before create data
    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }

    //    sebelum di remove
    @PreRemove
    protected void onDelete () {
        deletedAt = Instant.now();
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private Category category;


    public enum Category {
        music,
        sports,
        conference,
        festival,
        workshop,
        seminar,
        film,
        arts,
        business,
        science,
        tech,
        food,
        exhibition,
        travel,
        fashion
    }


}
