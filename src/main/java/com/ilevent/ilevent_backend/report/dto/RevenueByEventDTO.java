package com.ilevent.ilevent_backend.report.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RevenueByEventDTO {
    private BigDecimal totalRevenue;
    private List<TimeSegmentRevenueDTO> timeSegments;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TimeSegmentRevenueDTO {
        private String segmentName;
        private BigDecimal revenue;
    }
}

