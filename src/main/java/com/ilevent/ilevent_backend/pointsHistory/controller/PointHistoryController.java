package com.ilevent.ilevent_backend.pointsHistory.controller;

import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryRequestDto;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/points-history")
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;

    public PointHistoryController(PointHistoryService pointHistoryService) {
        this.pointHistoryService = pointHistoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<PointHistoryResponseDto> createPointsHistory(@RequestBody PointHistoryRequestDto pointsHistoryRequestDto) {
        PointHistoryResponseDto pointsHistory = pointHistoryService.createPointsHistory(pointsHistoryRequestDto);
        return ResponseEntity.ok(pointsHistory);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PointHistoryResponseDto>> getPointsHistoryByUserId(@PathVariable Long userId) {
        List<PointHistoryResponseDto> pointsHistories = pointHistoryService.getPointsHistoryByUserId(userId);
        return ResponseEntity.ok(pointsHistories);
    }

}
