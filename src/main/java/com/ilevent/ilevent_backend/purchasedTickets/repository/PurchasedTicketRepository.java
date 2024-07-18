package com.ilevent.ilevent_backend.purchasedTickets.repository;

import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PurchasedTicketRepository extends JpaRepository<TicketApply, Long> {
    @Query("SELECT ta FROM TicketApply ta " +
            "JOIN ta.transactionId t " +
            "JOIN t.event e " +
            "WHERE t.user.id = :userId " +
            "AND (e.date > CURRENT_DATE " +
            "OR (e.date = CURRENT_DATE AND e.time > CURRENT_TIME))")
    List<TicketApply> findUpcomingTicketsByUserId(Long userId);

    @Query("SELECT ta FROM TicketApply ta " +
            "JOIN ta.transactionId t " +
            "JOIN t.event e " +
            "WHERE t.user.id = :userId " +
            "AND (e.date < CURRENT_DATE " +
            "OR (e.date = CURRENT_DATE AND e.time <= CURRENT_TIME))")
    List<TicketApply> findCompletedTicketsByUserId(Long userId);
}

//    @Query("SELECT ta FROM TicketApply ta WHERE ta.transactionId.user.id = :userId AND ta.transactionId.event.date >= CURRENT_DATE")
//    List<TicketApply> findUpcomingTicketsByUserId(Long userId);
//
//    @Query("SELECT ta FROM TicketApply ta WHERE ta.transactionId.user.id = :userId AND ta.transactionId.event.date < CURRENT_DATE")
//    List<TicketApply> findCompletedTicketsByUserId(Long userId);
//}