package com.ilevent.ilevent_backend.voucherApply.repository;

import com.ilevent.ilevent_backend.voucherApply.entity.VoucherApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherApplyRepository extends JpaRepository<VoucherApply, Long> {
    List<VoucherApply> findAllByTransactionId(Long transactionId);
}
