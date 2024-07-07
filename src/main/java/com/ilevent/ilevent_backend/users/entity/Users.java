package com.ilevent.ilevent_backend.users.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Data
@Getter
@Setter
@Entity
@Table(name = "users", schema = "ilevent")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotNull
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @NotNull
    @Column(name = "username")
    private String username;


    @Size(max = 100)
    @NotNull
    @NotBlank
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name= "is_organizer", nullable = false)
    private Boolean isOrganizer;

    @Size(max = 100)
    @NotNull
    @NotBlank
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "total_points", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer totalPoints;

    @Size(max = 15)
    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "picture", length = Integer.MAX_VALUE)
    private String picture;

    @Column(name = "referral_code")
    private String referralCode;

//    @Column(name = "points",columnDefinition = "int default 0")
//    private Integer points = 0;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "update_at", nullable = false)
    private Instant updateAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;


    //    update data before create data
    @PrePersist
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