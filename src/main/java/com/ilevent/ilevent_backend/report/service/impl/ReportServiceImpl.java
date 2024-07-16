package com.ilevent.ilevent_backend.report.service.impl;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.events.repository.EventRepository;
import com.ilevent.ilevent_backend.report.entity.Report;
import com.ilevent.ilevent_backend.ticketApply.repository.TicketApplyRepository;
import com.ilevent.ilevent_backend.report.repository.ReportRepository;
import com.ilevent.ilevent_backend.report.service.ReportService;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    private void checkIfOrganizer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                boolean isOrganizer = userDetails.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ORGANIZER"));
                if (!isOrganizer) {
                    throw new RuntimeException("User is not an organizer");
                }
            } else {
                throw new RuntimeException("User details not found");
            }
        } else {
            throw new RuntimeException("User is not authenticated");
        }
    }

    @Override
    public List<Report> getReportsForInterval(int minutes) {

        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMinutes(minutes);
        return reportRepository.findReportsBetween(startTime, endTime);
    }

    @Override
    public List<Report> getDailyReports() {
        return reportRepository.findDailyReports();
    }

    @Override
    public List<Report> getWeeklyReports() {
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).plusDays(1);
        return reportRepository.findWeeklyReports(startDate, endDate);
    }

    @Override
    public List<Report> getMonthlyReports() {
        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
        return reportRepository.findMonthlyReports(startDate, endDate);
    }
}
//    @Override
//    public List<Report> getReportsForInterval(int minutes) {
//        LocalDateTime endTime = LocalDateTime.now();
//        LocalDateTime startTime = endTime.minusMinutes(minutes);
//        return reportRepository.findReportsBetween(startTime, endTime);
//    }
//
//    @Override
//    public List<Report> getDailyReports() {
//        return reportRepository.findDailyReports();
//    }
//
//    @Override
//    public List<Report> getWeeklyReports() {
//        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
//        return reportRepository.findWeeklyReports(startDate, endDate);
//    }
//
//    @Override
//    public List<Report> getMonthlyReports() {
//        LocalDate startDate = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
//        LocalDate endDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).plusDays(1);
//        return reportRepository.findMonthlyReports(startDate, endDate);
//    }
//}