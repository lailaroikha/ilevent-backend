package com.ilevent.ilevent_backend.transaction.repository;

import com.ilevent.ilevent_backend.transaction.entity.Transaction;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.event.organizer = :organizer AND t.transactionDate BETWEEN :periodStart AND :periodEnd")
    List<Transaction> findAllByOrganizerAndTransactionDateBetween(@Param("organizer") Users organizer, @Param("periodStart") LocalDate periodStart, @Param("periodEnd") LocalDate periodEnd);
}
