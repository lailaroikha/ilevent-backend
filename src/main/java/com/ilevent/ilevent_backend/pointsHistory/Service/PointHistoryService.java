package com.ilevent.ilevent_backend.pointsHistory.Service;

import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;

public interface PointHistoryService {
//    PointHistoryResponseDto addPointsHistory(PointHistoryRequestDto dto);
    PointsHistory addPointsHistory(Long userId, Integer points, String type);
    Integer getTotalPoints(Long userId);

}
