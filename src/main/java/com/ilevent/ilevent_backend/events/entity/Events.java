package com.ilevent.ilevent_backend.events.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ilevent.ilevent_backend.promoReferral.entity.PromoReferral;
import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.voucher.entity.Voucher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "events", schema = "ilevent")
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id", nullable = false)
    @JsonBackReference
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
    @Column(nullable = false, name="created_at")
    private Instant createdAt;

    @NotNull
    @Column(nullable = false, name="updated_at")
    private Instant updatedAt;

    @NotNull
    @Column(name="image",nullable = false)
    private String image;

    @ColumnDefault("false")
    @Column(name = "is_freeEvent")
    private Boolean isFreeEvent;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @Column(name = "ratting_rate")
    private Integer rattingRate;

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

//    @OneToMany(mappedBy = "eventId")
//    private List<Ticket> tickets;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false, length = 20)
    private CategoryType category;

    @OneToMany(mappedBy = "eventId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "eventId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Voucher> vouchers;

    @OneToOne(mappedBy = "eventsId", cascade = CascadeType.ALL)
    @JsonIgnore
    private PromoReferral promoReferral;

    public enum CategoryType {
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
