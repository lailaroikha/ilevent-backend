package com.ilevent.ilevent_backend.reviews.repository;

import com.ilevent.ilevent_backend.reviews.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {

}
