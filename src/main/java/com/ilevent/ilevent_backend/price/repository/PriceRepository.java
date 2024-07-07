package com.ilevent.ilevent_backend.price.repository;

import com.ilevent.ilevent_backend.price.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
