package com.ilevent.ilevent_backend.ticket.repository;

import com.ilevent.ilevent_backend.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
