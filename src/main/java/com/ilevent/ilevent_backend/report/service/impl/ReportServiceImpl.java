package com.ilevent.ilevent_backend.report.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.report.entity.Report;
import com.ilevent.ilevent_backend.ticketApply.repository.TicketApplyRepository;
import com.ilevent.ilevent_backend.report.repository.ReportRepository;
import com.ilevent.ilevent_backend.report.service.ReportService;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final TicketApplyRepository ticketApplyRepository;

    public ReportServiceImpl(ReportRepository reportRepository, TicketApplyRepository ticketApplyRepository) {
        this.reportRepository = reportRepository;
        this.ticketApplyRepository = ticketApplyRepository;
    }

//    @Override
//    public void updateReportForEvent(Long eventId, Long organizerId) {
//        Integer totalAttendance = ticketApplyRepository.getTotalAttendanceByEventId(eventId);
//        if (totalAttendance == null) {
//            totalAttendance = 0;
//        }
//
//        Report report = reportRepository.findByEventIdAndOrganizerId(eventId, organizerId);
//
//        if (report == null) {
//            report = new Report();
//            Events event = new Events();
//            event.setId(eventId);
//            report.setEventId(event);
//
//            Users organizer = new Users();
//            organizer.setId(organizerId);
//            report.setOrganizer(organizer);
//
//            report.setCreatedAt(Instant.now());
//            report.setReportDate(LocalDate.now());
//        }
//
//        report.setAttendees(totalAttendance);
//        report.setUpdatedAt(Instant.now());
//
//        reportRepository.save(report);
//    }

    @Override
    public Integer getTotalAttendanceForEvent(Long eventId) {
        Integer totalAttendance = ticketApplyRepository.getTotalAttendanceByEventId(eventId);
        if (totalAttendance == null) {
            totalAttendance = 0;
        }

        Events event = new Events();
        event.setId(eventId);
        Report report = reportRepository.findByEventIdAndOrganizerId(event, null);
        if (report == null) {
            report = new Report();
            report.setEventId(event);
            report.setCreatedAt(Instant.now());
            report.setReportDate(LocalDate.now());
        }
        report.setAttendees(totalAttendance);
        report.setUpdatedAt(Instant.now());

        reportRepository.save(report);

        return totalAttendance;
    }
//        Integer totalAttendance = ticketApplyRepository.getTotalAttendanceByEventId(eventId);
//        return totalAttendance != null ? totalAttendance : 0;
//    }

    @Override
    public Integer getTotalAttendanceForOrganizer(Long organizerId) {
        Integer totalAttendance = ticketApplyRepository.getTotalAttendanceByOrganizerId(organizerId);
        if (totalAttendance == null) {
            totalAttendance = 0;
        }

        Users organizer = new Users();
        organizer.setId(organizerId);
        Report report = reportRepository.findByEventIdAndOrganizerId(null, organizer);
        if (report == null) {
            report = new Report();
            report.setOrganizer(organizer);
            report.setCreatedAt(Instant.now());
            report.setReportDate(LocalDate.now());
        }
        report.setAttendees(totalAttendance);
        report.setUpdatedAt(Instant.now());

        reportRepository.save(report);

        return totalAttendance;
    }
//        Integer totalAttendance = ticketApplyRepository.getTotalAttendanceByOrganizerId(organizerId);
//        return totalAttendance != null ? totalAttendance : 0;
//    }
}
