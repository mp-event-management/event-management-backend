package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "event_reviews")
public class EventReview {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_reviews_id_gen")
    @SequenceGenerator(name = "event_reviews_id_gen", sequenceName = "event_reviews_event_review_id_seq", allocationSize = 1)
    @Column(name = "event_review_id", nullable = false)
    private Integer eventReviewId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersAccount user;

    @Column(name = "rating", precision = 2, scale = 1)
    private BigDecimal rating;

    @Column(name = "review_text", length = Integer.MAX_VALUE)
    private String reviewText;

    @Column(name = "feedback", length = Integer.MAX_VALUE)
    private String feedback;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

}