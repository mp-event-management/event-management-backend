package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "referral_codes")
public class ReferralCode {
//    @Id
//    @SequenceGenerator(name = "referral_codes_id_gen", sequenceName = "promotions_promotion_id_seq", allocationSize = 1)
//    @Column(name = "referral_code", nullable = false, length = Integer.MAX_VALUE)
//    private String referralCodeId;

    @Id
    @GeneratedValue(generator = "codeGenerator")
    @GenericGenerator(
            name = "codeGenerator",
            strategy = "com.eventmanagement.EventManagementBackend.infrastructure.referralCode.CodeGeneratorUseCase"
    )
    @Column(name = "referral_code", nullable = false, length = 8)
    private String referralCodeId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersAccount user;

    @ColumnDefault("0")
    @Column(name = "total_usage")
    private Integer totalUsage;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;
    @OneToMany(mappedBy = "referralCode")
    private Set<ReferralUsage> referralUsages = new LinkedHashSet<>();

    @PrePersist
    public void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = OffsetDateTime.now();
        }
        this.updatedAt = OffsetDateTime.now();
        if (totalUsage == null) {
            totalUsage = 0;
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