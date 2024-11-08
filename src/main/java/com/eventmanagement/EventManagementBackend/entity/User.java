package com.eventmanagement.EventManagementBackend.entity;
import com.eventmanagement.EventManagementBackend.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "fullname", nullable = false, length = 150)
    private String fullname;

    @Size(max = 150)
    @NotNull
    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Size(max = 100)
    @Column(name = "referral_code", length = 100)
    private String referralCode;

    @ColumnDefault("0")
    @Column(name = "referral_points", precision = 10, scale = 5)
    private BigDecimal referralPoints;

    @ColumnDefault("false")
    @Column(name = "is_first_time_discount")
    private Boolean isFirstTimeDiscount;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "role not null", nullable = false)
    private Role role;

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }
}