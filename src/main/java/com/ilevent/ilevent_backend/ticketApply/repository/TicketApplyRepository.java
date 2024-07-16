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

}
