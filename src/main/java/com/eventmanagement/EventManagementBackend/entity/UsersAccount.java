package com.eventmanagement.EventManagementBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users_accounts")
public class UsersAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_accounts_id_gen")
    @SequenceGenerator(name = "users_accounts_id_gen", sequenceName = "users_accounts_user_id_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference  // Marks this as the back reference
    private Role role;

    @Size(max = 150)
    @NotNull
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Size(max = 200)
    @NotNull
    @Column(name = "email", nullable = false, length = 200)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false, length = Integer.MAX_VALUE)
    private String password;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "city_id")
    private City city;

    @Size(max = 255)
    @Column(name = "address")
    private String address;

    @Size(max = 255)
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @NotNull
    @Column(name = "referral_code", nullable = false, length = Integer.MAX_VALUE)
    private String referralCode;

    @Column(name = "used_referral_code", length = Integer.MAX_VALUE)
    private String usedReferralCode;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_first_time_discount", nullable = false)
    private Boolean isFirstTimeDiscount = false;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    // RELATIONSHIP ENTITY
    @OneToMany(mappedBy = "user")
    private Set<EventReview> eventReviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userOrganizer")
    private Set<Event> events = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "userOrganizer")
//    private List<Promotion> promotions = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<ReferralCode> referralCodes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ReferralPoint> referralPoints = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ReferralUsage> referralUsages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userCustomer")
    private Set<Transaction> transactions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserEventHistory> userEventHistories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "userOrganizer")
    private Set<Promotion> promotions = new LinkedHashSet<>();

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