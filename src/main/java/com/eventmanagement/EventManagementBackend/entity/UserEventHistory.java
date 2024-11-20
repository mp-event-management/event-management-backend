package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_event_history")
public class UserEventHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_event_history_id_gen")
    @SequenceGenerator(name = "user_event_history_id_gen", sequenceName = "user_event_history_user_event_history_id_seq", allocationSize = 1)
    @Column(name = "user_event_history_id", nullable = false)
    private Integer userEventHistoryId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersAccount user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @NotNull
    @Column(name = "ticket_quantity", nullable = false)
    private Integer ticketQuantity;

    @Size(max = 50)
    @NotNull
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "joined_at")
    private OffsetDateTime joinedAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

}