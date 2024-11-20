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
@Table(name = "promotion_types")
public class PromotionType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "promotion_types_id_gen")
    @SequenceGenerator(name = "promotion_types_id_gen", sequenceName = "promotion_types_promotion_type_id_seq", allocationSize = 1)
    @Column(name = "promotion_type_id", nullable = false)
    private Integer promotionTypeId;

    @Size(max = 150)
    @NotNull
    @Column(name = "promo_name", nullable = false, length = 150)
    private String promoName;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

}