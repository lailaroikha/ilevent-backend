package com.ilevent.ilevent_backend.report.controller;

import com.ilevent.ilevent_backend.report.entity.Report;
import com.ilevent.ilevent_backend.report.service.ReportService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

@RolesAllowed("ROLE_ORGANIZER")
@GetMapping
    public List<Report> getReports(@RequestParam String interval, @RequestParam(required = false) Integer value) {
        switch (interval.toLowerCase()) {
            case "minute":
                if (value == null) throw new IllegalArgumentException("Value is required for minute interval");
                return reportService.getReportsForInterval(value);
            case "daily":
                return reportService.getDailyReports();
            case "weekly":
                return reportService.getWeeklyReports();
            case "monthly":
                return reportService.getMonthlyReports();
            default:
                throw new IllegalArgumentException("Invalid interval");
        }
    }
}
//    @GetMapping("/interval")
//    public List<Report> getReportsForInterval(@RequestParam int minutes) {
//        return reportService.getReportsForInterval(minutes);
//    }
//
//    @GetMapping("/daily")
//    public List<Report> getDailyReports() {
//        return reportService.getDailyReports();
//    }
//
//    @GetMapping("/weekly")
//    public List<Report> getWeeklyReports() {
//        return reportService.getWeeklyReports();
//    }
//
//    @GetMapping("/monthly")
//    public List<Report> getMonthlyReports() {
//        return reportService.getMonthlyReports();
//    }
//}