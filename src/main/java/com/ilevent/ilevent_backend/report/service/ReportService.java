package com.ilevent.ilevent_backend.report.service;

public interface ReportService {
//    void updateReportForEvent(Long eventId, Long organizerId);
    Integer getTotalAttendanceForEvent(Long eventId);
    Integer getTotalAttendanceForOrganizer(Long organizerId);
}
