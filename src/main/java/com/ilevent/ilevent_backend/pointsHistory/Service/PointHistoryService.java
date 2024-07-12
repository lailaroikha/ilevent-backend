package com.ilevent.ilevent_backend.pointsHistory.Service;

import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;

import java.time.LocalDate;

public interface PointHistoryService {
//    PointHistoryResponseDto addPointsHistory(PointHistoryRequestDto dto);
    PointsHistory addPointsHistory(Long userId, Integer points, String type, LocalDate localDate);
//    Integer getTotalPoints(Long userId);

}
