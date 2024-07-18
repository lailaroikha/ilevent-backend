package com.ilevent.ilevent_backend.reviews.repository;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.reviews.entity.Reviews;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findByEvent(Events event);
    boolean existsByUserAndEvent(Users user, Events event);
}
