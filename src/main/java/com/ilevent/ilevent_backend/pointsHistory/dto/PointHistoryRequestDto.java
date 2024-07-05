package com.ilevent.ilevent_backend.pointsHistory.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class PointHistoryRequestDto {
    private Long userId;
    private Integer points;
    private String type;
    private Instant createdAt;
    private Instant updateAt;
}
