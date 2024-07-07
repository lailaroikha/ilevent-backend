package com.ilevent.ilevent_backend.pointsHistory.Service;

//import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryRequestDto;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryRequestDto;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryResponseDto;

import java.util.List;

public interface PointHistoryService {
    PointHistoryResponseDto addPointsHistory(PointHistoryRequestDto dto);
    Integer getTotalPoints(Long userId);

}
