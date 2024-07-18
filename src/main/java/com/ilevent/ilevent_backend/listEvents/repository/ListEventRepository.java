package com.ilevent.ilevent_backend.listEvents.repository;

import com.ilevent.ilevent_backend.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ListEventRepository extends JpaRepository<Transaction, Long> {

}
