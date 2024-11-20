package com.eventmanagement.EventManagementBackend.infrastructure.cities.repository;

import com.eventmanagement.EventManagementBackend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
}
