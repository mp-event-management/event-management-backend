package com.eventmanagement.EventManagementBackend.infrastructure.events.specification;

import com.eventmanagement.EventManagementBackend.entity.Event;
import org.springframework.data.jpa.domain.Specification;

public class FilterEventSpecifications {

    public static Specification<Event> hasCategory(Integer categoryId) {
        return (root, query, criteriaBuilder) -> categoryId == null ? null
                : criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId);
    }

    public static Specification<Event> hasCity(Integer cityId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city").get("cityId"), cityId);
    }

    public static Specification<Event> hasTitleLike(String searchQuery) {
        return (root, query, criteriaBuilder) -> searchQuery == null || searchQuery.isEmpty()
                ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%");
    }
}
