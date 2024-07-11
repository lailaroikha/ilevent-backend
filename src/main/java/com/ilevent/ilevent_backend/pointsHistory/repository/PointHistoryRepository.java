package com.ilevent.ilevent_backend.pointsHistory.repository;

import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointHistoryRepository extends JpaRepository<PointsHistory, Long> {
//    List<PointsHistory> findByUserId(Long userId);
}

