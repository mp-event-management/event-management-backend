package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_id_gen")
    @SequenceGenerator(name = "events_id_gen", sequenceName = "events_event_id_seq", allocationSize = 1)
    @Column(name = "event_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Size(max = 255)
    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @ColumnDefault("CURRENT_DATE")
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "time_from", nullable = false)
    private OffsetDateTime timeFrom;

    @NotNull
    @Column(name = "date_until", nullable = false)
    private LocalDate dateUntil;

    @NotNull
    @Column(name = "time_until", nullable = false)
    private OffsetDateTime timeUntil;

    @Size(max = 150)
    @NotNull
    @Column(name = "category", nullable = false, length = 150)
    private String category;

    @NotNull
    @Column(name = "images_url", nullable = false, length = Integer.MAX_VALUE)
    private String imagesUrl;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "price", nullable = false, precision = 10, scale = 5)
    private BigDecimal price;

    @NotNull
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @NotNull
    @Column(name = "current_available_seats", nullable = false)
    private Integer currentAvailableSeats;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

}