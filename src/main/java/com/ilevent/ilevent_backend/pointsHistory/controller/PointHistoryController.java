//package com.ilevent.ilevent_backend.pointsHistory.controller;
//
//import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/api/points-history")
//public class PointHistoryController {
//    private final PointHistoryService pointHistoryService;
//
//    public PointHistoryController(PointHistoryService pointHistoryService) {
//        this.pointHistoryService = pointHistoryService;
//    }
//
////    @PostMapping
////    public PointHistoryResponseDto addPointsHistory(@Valid @RequestBody PointHistoryRequestDto dto) {
////        return pointHistoryService.addPointsHistory(dto);
////    }
//
//    @GetMapping("/{userId}")
//    public Integer getTotalPoints(@PathVariable Long userId) {
//        return pointHistoryService.getTotalPoints(userId);
//    }
//
//}
