package com.eventmanagement.EventManagementBackend.infrastructure.categories.repository;

import com.eventmanagement.EventManagementBackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
