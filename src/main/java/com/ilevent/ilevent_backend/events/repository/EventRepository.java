package com.ilevent.ilevent_backend.events.repository;

import com.ilevent.ilevent_backend.events.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {

}
