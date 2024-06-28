package com.ilevent.ilevent_backend.event.repository;

import com.ilevent.ilevent_backend.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
