package com.ilevent.ilevent_backend.pointsHistory.controller;

import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryRequestDto;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryResponseDto;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/points-history")
public class PointHistoryController {
    private final PointHistoryService pointHistoryService;

    public PointHistoryController(PointHistoryService pointHistoryService) {
        this.pointHistoryService = pointHistoryService;
    }

    @PostMapping
    public PointHistoryResponseDto addPointsHistory(@Valid @RequestBody PointHistoryRequestDto dto) {
        return pointHistoryService.addPointsHistory(dto);
    }

    @GetMapping("/total/{userId}")
    public Integer getTotalPoints(@PathVariable Long userId) {
        return pointHistoryService.getTotalPoints(userId);
    }

}
