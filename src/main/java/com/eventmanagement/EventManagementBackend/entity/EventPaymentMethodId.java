package com.eventmanagement.EventManagementBackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class EventPaymentMethodId implements java.io.Serializable {
    private static final long serialVersionUID = 1131901693337665168L;
    @NotNull
    @Column(name = "event_id", nullable = false)
    private Integer eventId;

    @NotNull
    @Column(name = "payment_method_id", nullable = false)
    private Integer paymentMethodId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventPaymentMethodId entity = (EventPaymentMethodId) o;
        return Objects.equals(this.eventId, entity.eventId) &&
                Objects.equals(this.paymentMethodId, entity.paymentMethodId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, paymentMethodId);
    }

}