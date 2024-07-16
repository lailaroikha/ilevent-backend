package com.ilevent.ilevent_backend.report.controller;

import com.ilevent.ilevent_backend.report.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

//    @PostMapping("/update")
//    @PreAuthorize("hasRole('ORGANIZER')")
//    public ResponseEntity<Void> updateReport(@RequestParam Long eventId, @RequestParam Long organizerId) {
//        reportService.updateReportForEvent(eventId, organizerId);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/total-attendance")
    @PreAuthorize("hasRole('ORGANIZER')")
    public ResponseEntity<String> getTotalAttendance(@RequestParam(required = false) Long eventId,
                                                     @RequestParam(required = false) Long organizerId) {
        if (eventId != null) {
            Integer totalAttendanceForEvent = reportService.getTotalAttendanceForEvent(eventId);
            return ResponseEntity.ok("Total attendance for event " + eventId + ": " + totalAttendanceForEvent);
        } else if (organizerId != null) {
            Integer totalAttendanceForOrganizer = reportService.getTotalAttendanceForOrganizer(organizerId);
            return ResponseEntity.ok("Total attendance for organizer " + organizerId + ": " + totalAttendanceForOrganizer);
        } else {
            return ResponseEntity.badRequest().body("Event ID or Organizer ID must be provided.");
        }
    }
}
