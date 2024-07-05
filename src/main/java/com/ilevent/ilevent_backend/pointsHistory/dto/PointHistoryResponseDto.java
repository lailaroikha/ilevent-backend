package com.ilevent.ilevent_backend.pointsHistory.dto;

import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import lombok.Data;

import java.time.Instant;

@Data
public class PointHistoryResponseDto {
    private Integer id;
    private Long userId;
    private Integer points;
    private String type;
    private Instant createdAt;
    private Instant updateAt;

    public static PointHistoryResponseDto fromEntity(PointsHistory pointsHistory) {
        PointHistoryResponseDto dto = new PointHistoryResponseDto();
        dto.setId(pointsHistory.getId());
        dto.setUserId(pointsHistory.getUser().getId());
        dto.setPoints(pointsHistory.getPoints());
        dto.setType(pointsHistory.getType());
        dto.setCreatedAt(pointsHistory.getCreatedAt());
        dto.setUpdateAt(pointsHistory.getUpdateAt());
        return dto;
    }
}
