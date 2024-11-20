package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cities_id_gen")
    @SequenceGenerator(name = "cities_id_gen", sequenceName = "cities_city_id_seq", allocationSize = 1)
    @Column(name = "city_id", nullable = false)
    private Integer cityId;

    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

//    @OneToMany(mappedBy = "city")
//    private Set<Event> events = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "city")
//    private Set<UsersAccount> usersAccounts = new LinkedHashSet<>();

}