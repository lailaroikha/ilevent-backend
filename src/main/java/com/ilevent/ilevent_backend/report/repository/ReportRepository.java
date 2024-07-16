package com.ilevent.ilevent_backend.report.repository;

import com.ilevent.ilevent_backend.events.entity.Events;
import com.ilevent.ilevent_backend.report.entity.Report;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
//    Report findByEventIdAndOrganizerId(Long eventId, Long organizerId);
    Report findByEventIdAndOrganizerId(Events eventId, Users organizer);
}
