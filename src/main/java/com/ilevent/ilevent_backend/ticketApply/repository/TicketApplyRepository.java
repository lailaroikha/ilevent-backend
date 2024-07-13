package com.ilevent.ilevent_backend.ticketApply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketApplyRepository extends JpaRepository<TicketApplyRepository, Long> {
}
