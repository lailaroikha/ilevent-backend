//package com.ilevent.ilevent_backend.purchasedTickets.repository;
//
//import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
//import com.ilevent.ilevent_backend.users.entity.Users;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface PurchasedTicketRepository extends JpaRepository<TicketApply, Long> {
//    @Query("SELECT ta FROM TicketApply ta " +
//            "JOIN ta.transactionId t " +
//            "JOIN ta.ticketId ti " +
//            "JOIN ti.eventId e " +
//            "WHERE t.user.id = :userId AND e.date > CURRENT_DATE")
//    List<TicketApply> findUpcomingTickets(@Param("userId") Long userId);
//
//    @Query("SELECT ta FROM TicketApply ta " +
//            "JOIN ta.transactionId t " +
//            "JOIN ta.ticketId ti " +
//            "JOIN ti.eventId e " +
//            "WHERE t.user.id = :userId AND e.date <= CURRENT_DATE")
//    List<TicketApply> findCompletedTickets(@Param("userId") Long userId);
//}