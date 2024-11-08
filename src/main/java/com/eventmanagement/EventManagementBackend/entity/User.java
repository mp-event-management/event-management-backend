package com.eventmanagement.EventManagementBackend.entity;

import com.eventmanagement.EventManagementBackend.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
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

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "role not null", nullable = false)
    private Role role;

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
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

}