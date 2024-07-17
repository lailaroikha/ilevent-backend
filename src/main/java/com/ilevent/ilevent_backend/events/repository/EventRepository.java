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
//    @Query("SELECT e FROM Events e JOIN TicketApply ta ON ta.ticketId.id = e.id WHERE ta.transactionId.user.id = :userId AND (e.date > :date OR (e.date = :date AND e.time > :time))")
//    List<Events> findUpcomingEventsByUser(@Param("userId") Long userId, @Param("date") LocalDate date, @Param("time") LocalTime time);
//    @Query("SELECT e FROM Events e JOIN TicketApply ta ON ta.ticketId.id = e.id WHERE ta.transactionId.user.id = :userId AND (e.date < :date OR (e.date = :date AND e.time < :time))")
//    List<Events> findCompletedEventsByUser(@Param("userId") Long userId, @Param("date") LocalDate date, @Param("time") LocalTime time);
}
//    @Query("SELECT e FROM Events e JOIN TicketApply ta ON e.id = ta.ticketId.id WHERE ta.transactions.user.id = :userId AND (e.date > :date OR (e.date = :date AND e.time > :time))")
//    List<Events> findUpcomingEventsByUser(Long userId, LocalDate date, LocalTime time);
//
//    @Query("SELECT e FROM Events e JOIN TicketApply ta ON e.id = ta.e.id WHERE ta.transactions.user.id = :userId AND (e.date < :date OR (e.date = :date AND e.time < :time))")
//    List<Events> findCompletedEventsByUser(Long userId, LocalDate date, LocalTime time);
//}