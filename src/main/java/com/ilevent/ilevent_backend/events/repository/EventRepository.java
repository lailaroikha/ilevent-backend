package com.ilevent.ilevent_backend.events.repository;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {

    List<Events> findByCategory(Events.CategoryType category);

    List<Events> findByDate(LocalDate date);

    List<Events> findByIsFreeEvent(Boolean isFreeEvent);

    @Query("SELECT e FROM Events e JOIN e.tickets t GROUP BY e HAVING SUM(t.availableSeats) >= :availableSeats")
    List<Events> findByAvailableSeatsGreaterThanEqual(@Param("availableSeats") Integer availableSeats);

    @Query("SELECT e FROM Events e JOIN e.tickets t WHERE e.category = :category AND e.date = :date AND e.isFreeEvent = :isFreeEvent GROUP BY e HAVING SUM(t.availableSeats) >= :availableSeats")
    List<Events> findByCategoryAndDateAndIsFreeEventAndAvailableSeatsGreaterThanEqual(
            @Param("category") Events.CategoryType category,
            @Param("date") LocalDate date,
            @Param("isFreeEvent") Boolean isFreeEvent,
            @Param("availableSeats") Integer availableSeats
            //location
            //out of stock
            //popular
            //murahke mahal
            //mahal ke murah
    );
}
