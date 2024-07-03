package com.ilevent.ilevent_backend.eventcategory.repository;

import com.ilevent.ilevent_backend.eventcategory.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    
}
