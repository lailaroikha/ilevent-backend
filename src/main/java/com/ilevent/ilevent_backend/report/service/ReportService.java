package com.ilevent.ilevent_backend.report.service;

import com.ilevent.ilevent_backend.report.entity.Report;

import java.util.List;

public interface ReportService {
    List<Report> getReportsForInterval(int minutes);
    List<Report> getDailyReports();
    List<Report> getWeeklyReports();
    List<Report> getMonthlyReports();
}
