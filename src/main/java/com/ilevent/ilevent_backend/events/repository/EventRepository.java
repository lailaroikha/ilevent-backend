package com.ilevent.ilevent_backend.events.repository;

import com.ilevent.ilevent_backend.events.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Events, Long>, JpaSpecificationExecutor<Events> {

    List<Events> findByCategory(Events.CategoryType category);
    List<Events> findByDate(LocalDate date);
    List<Events> findByIsFreeEvent(Boolean isFreeEvent);
}
