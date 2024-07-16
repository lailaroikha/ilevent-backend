package com.ilevent.ilevent_backend.report.repository;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.report.entity.Report;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM Report r WHERE r.createdAt BETWEEN :startTime AND :endTime")
    List<Report> findReportsBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    @Query("SELECT r FROM Report r WHERE r.reportDate = CURRENT_DATE")
    List<Report> findDailyReports();

    @Query("SELECT r FROM Report r WHERE r.reportDate >= :startDate AND r.reportDate < :endDate")
    List<Report> findWeeklyReports(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Report r WHERE r.reportDate >= :startDate AND r.reportDate < :endDate")
    List<Report> findMonthlyReports(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
    //    @Query("SELECT r FROM Report r WHERE r.createdAt BETWEEN :startTime AND :endTime")
//    List<Report> findReportsBetween(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
//
//    @Query("SELECT r FROM Report r WHERE r.reportDate = CURRENT_DATE")
//    List<Report> findDailyReports();
//
//    @Query("SELECT r FROM Report r WHERE r.reportDate >= :startDate AND r.reportDate < :endDate")
//    List<Report> findWeeklyReports(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//
//    @Query("SELECT r FROM Report r WHERE r.reportDate >= :startDate AND r.reportDate < :endDate")
//    List<Report> findMonthlyReports(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
//}