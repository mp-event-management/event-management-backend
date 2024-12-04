package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotions_id_gen")
    @SequenceGenerator(name = "promotions_id_gen", sequenceName = "promotions_promotion_id_seq", allocationSize = 1)
    @Column(name = "promotion_id", nullable = false)
    private Integer promotionId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @NotNull
    @Column(name = "promotion_type", nullable = false)
    private String promotionType;

    @Size(max = 255)
    @NotNull
    @Column(name = "promotion_code", nullable = false)
    private String promotionCode;

    @ColumnDefault("NULL")
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(name = "available_uses")
    private Integer availableUses;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private OffsetDateTime startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private OffsetDateTime endDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @OneToMany(mappedBy = "promotion")
    private Set<Transaction> transactions = new LinkedHashSet<>();

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    @PreRemove
    protected void onRemove() {
        deletedAt = OffsetDateTime.now();
    }

}