package com.ilevent.ilevent_backend.report.controller;


import com.ilevent.ilevent_backend.report.dto.RevenueByEventDTO;
import com.ilevent.ilevent_backend.report.service.ReportService;
import com.ilevent.ilevent_backend.responses.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    @GetMapping("/revenue/hourly/today")
    public ResponseEntity<Response<Object>> getHourlyRevenueToday(Authentication authentication) {
        RevenueByEventDTO response = reportService.getRevenueByHourToday(authentication);
        return Response.success("Hourly revenue for today successfully retrieved.", response);
    }
}
