package com.ilevent.ilevent_backend.report.repository;

import com.ilevent.ilevent_backend.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByEventIdAndOrganizerId(Long eventId, Long organizerId);
}
