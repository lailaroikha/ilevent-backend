package com.ilevent.ilevent_backend.ticketApply.repository;

import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
import com.ilevent.ilevent_backend.transaction.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketApplyRepository extends JpaRepository<TicketApply, Long> {
    List<TicketApply> findAllByTransactionId(Transaction transactionId);

    //menghitung jumlah total tiket yang terjual untuk suatu event tertentu
    @Query("SELECT SUM(ta.quantity) FROM TicketApply ta WHERE ta.ticketId.eventId.id = :eventId")
    Integer getTotalAttendanceByEventId(@Param("eventId") Long eventId);
    //menghitung jumlah ticket yang terjual untuk setiap orginizer
//    @Query("SELECT SUM(ta.quantity) FROM TicketApply ta WHERE ta.ticketId.eventId.organizer.id = :organizerId")
//    Integer getTotalAttendanceByOrganizerId(@Param("organizerId") Long organizerId);
    @Query("SELECT SUM(ta.quantity) FROM TicketApply ta " +
            "JOIN ta.ticketId t " +
            "JOIN t.eventId e " +
            "WHERE e.organizer.id = :organizerId")
    Integer getTotalAttendanceByOrganizerId(@Param("organizerId") Long organizerId);
}
