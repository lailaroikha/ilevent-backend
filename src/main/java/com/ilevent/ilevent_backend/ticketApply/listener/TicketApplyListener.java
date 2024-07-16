//package com.ilevent.ilevent_backend.ticketApply.listener;
//
//import com.ilevent.ilevent_backend.report.service.ReportService;
//import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
//import jakarta.persistence.PostPersist;
//import jakarta.persistence.PostUpdate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TicketApplyListener {
//    private final ReportService reportService;
//
//    public TicketApplyListener(ReportService reportService) {
//        this.reportService = reportService;
//    }
//    @PostPersist
//    @PostUpdate
//    public void onPostPersistOrUpdate(TicketApply ticketApply) {
//        if (ticketApply.getTicketId() != null && ticketApply.getTicketId().getEventId() != null) {
//            Long eventId = ticketApply.getTicketId().getEventId().getId();
//            Long organizerId = ticketApply.getTicketId().getEventId().getOrganizer().getId();
//            reportService.updateReportForEvent(eventId, organizerId);
//        }
//    }
//}
