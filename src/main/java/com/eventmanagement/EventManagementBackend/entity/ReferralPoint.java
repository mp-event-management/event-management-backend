package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "referral_points")
public class ReferralPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "referral_points_id_gen")
    @SequenceGenerator(name = "referral_points_id_gen", sequenceName = "referral_points_referral_point_id_seq", allocationSize = 1)
    @Column(name = "referral_point_id", nullable = false)
    private Integer referralPointId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersAccount user; //owner of the code, determined at new registration

    @NotNull
    @ColumnDefault("10000")
    @Column(name = "points", nullable = false)
    private Integer points;

    @NotNull
    @ColumnDefault("(now() + '90 days'::interval)")
    @Column(name = "expiry_date", nullable = false)
    private OffsetDateTime expiryDate;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
        this.updatedAt = OffsetDateTime.now();
        if (points == null) {
            points = 10000;
        }
        if (expiryDate == null) {
            expiryDate = OffsetDateTime.now().plusDays(90);
        }
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