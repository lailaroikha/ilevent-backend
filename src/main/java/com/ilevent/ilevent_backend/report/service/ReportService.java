package com.ilevent.ilevent_backend.report.service;

import com.ilevent.ilevent_backend.report.dto.RevenueByEventDTO;
import org.springframework.security.core.Authentication;

public interface ReportService {
    RevenueByEventDTO getRevenueByHourToday(Authentication authentication);
}
