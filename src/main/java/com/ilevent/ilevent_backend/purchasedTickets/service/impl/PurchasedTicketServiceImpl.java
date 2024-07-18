//package com.ilevent.ilevent_backend.purchasedTickets.service.impl;
//
//import com.ilevent.ilevent_backend.purchasedTickets.dto.PurchasedTicketsResponseDto;
//import com.ilevent.ilevent_backend.purchasedTickets.repository.PurchasedTicketRepository;
//import com.ilevent.ilevent_backend.purchasedTickets.service.PurchasedTicketService;
//import com.ilevent.ilevent_backend.ticketApply.entity.TicketApply;
//import com.ilevent.ilevent_backend.users.entity.Users;
//import com.ilevent.ilevent_backend.users.repository.UserRepository;
//import jakarta.annotation.security.RolesAllowed;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.hibernate.query.sqm.tree.SqmNode.log;
//
//@Service
//public class PurchasedTicketServiceImpl implements PurchasedTicketService {
//    private final PurchasedTicketRepository purchasedTicketRepository;
//    private final UserRepository userRepository;
//    public PurchasedTicketServiceImpl(PurchasedTicketRepository purchasedTicketRepository, UserRepository userRepository) {
//        this.purchasedTicketRepository = purchasedTicketRepository;
//        this.userRepository = userRepository;
//    }
//    @Transactional
//    @Override
//    public List<PurchasedTicketsResponseDto> getPurchasedTickets() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        Users user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (!authentication.getAuthorities().stream()
//                .anyMatch(a -> a.getAuthority().equals("ROLE_PERSONAL"))) {
//            throw new RuntimeException("Only personal users can view purchased tickets");
//        }
//
//        Long userId = user.getId();
//
//        List<TicketApply> upcomingTickets = purchasedTicketRepository.findUpcomingTickets(userId);
//        List<TicketApply> completedTickets = purchasedTicketRepository.findCompletedTickets(userId);
//
//        List<PurchasedTicketsResponseDto> upcomingEvents = upcomingTickets.stream()
//                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
//                .collect(Collectors.toList());
//
//        List<PurchasedTicketsResponseDto> completedEvents = completedTickets.stream()
//                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
//                .collect(Collectors.toList());
//
//        upcomingEvents.addAll(completedEvents);
//        return upcomingEvents;
//    }
//}
////    @Transactional
////    @Override
////    public List<PurchasedTicketsResponseDto> getPurchasedTickets() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String email = authentication.getName(); // Mengambil email dari token
////
////        Users user = userRepository.findByEmail(email)
////                .orElseThrow(() -> new RuntimeException("User not found"));
////        log.info("User found: " + user.getEmail() + ", Is Organizer: " + user.getOrganizer());
////
////        if (Boolean.TRUE.equals(user.getOrganizer())) {
////            log.error("User with email " + email + " is an organizer");
////            throw new RuntimeException("User is an organizer and cannot view purchased tickets");
////        }
////
////        Long userId = user.getId();
////
////        List<TicketApply> upcomingTickets = purchasedTicketRepository.findUpcomingTickets(userId);
////        List<TicketApply> completedTickets = purchasedTicketRepository.findCompletedTickets(userId);
////
////        List<PurchasedTicketsResponseDto> upcomingEvents = upcomingTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        List<PurchasedTicketsResponseDto> completedEvents = completedTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        upcomingEvents.addAll(completedEvents);
////        return upcomingEvents;
////    }
////}
//
//
//    //    @Transactional
////    @Override
////    public List<PurchasedTicketsResponseDto> getPurchasedTickets() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        String email = authentication.getName(); // Assumes email is the principal
////
////        Users user = userRepository.findByEmail(email)
////                .orElseThrow(() -> new RuntimeException("User not found"));
////        log.info("User found: " + user.getEmail() + ", Is Organizer: " + user.getOrganizer());
////
////        if (Boolean.TRUE.equals(user.getOrganizer())) {
////            log.error("User with email " + email + " is an organizer");
////            throw new RuntimeException("User is an organizer and cannot view purchased tickets");
////        }
////
////        Long userId = user.getId();
////
////        List<TicketApply> upcomingTickets = purchasedTicketRepository.findUpcomingTickets(userId);
////        List<TicketApply> completedTickets = purchasedTicketRepository.findCompletedTickets(userId);
////
////        List<PurchasedTicketsResponseDto> upcomingEvents = upcomingTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        List<PurchasedTicketsResponseDto> completedEvents = completedTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        upcomingEvents.addAll(completedEvents);
////        return upcomingEvents;
////    }
////}
//
//
////    @Transactional
////    @Override
////    public List<PurchasedTicketsResponseDto> getPurchasedTickets(String email) {
////        Users user = userRepository.findByEmail(email)
////                .orElseThrow(() -> new RuntimeException("User not found"));
////        log.info("User found: " + user.getEmail() + ", Is Organizer: " + user.getOrganizer());
////
////        if (Boolean.TRUE.equals(user.getOrganizer())) {
////            log.error("User with email " + email + " is an organizer");
////            throw new RuntimeException("User is an organizer and cannot view purchased tickets");
////        }
////
////        Long userId = user.getId();
////
////        List<TicketApply> upcomingTickets = purchasedTicketRepository.findUpcomingTickets(userId);
////        List<TicketApply> completedTickets = purchasedTicketRepository.findCompletedTickets(userId);
////
////        List<PurchasedTicketsResponseDto> upcomingEvents = upcomingTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        List<PurchasedTicketsResponseDto> completedEvents = completedTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        upcomingEvents.addAll(completedEvents);
////        return upcomingEvents;
////    }
////}
////        // Memeriksa peran pengguna
////        Users user = userRepository.findById(userId)
////                .orElseThrow(() -> new RuntimeException("User not found"));
////
////        if (!user.isOrganizer()) { // Pastikan ini memeriksa role PERSONAL
////            throw new RuntimeException("User does not have the PERSONAL role");
////        }
//////        Users user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
//////        if (!"ROLE_PERSONAL".equals(user.getOrganizer())) {
//////            throw new SecurityException("Access denied");
//////        }
////
////        // Mengambil tiket yang akan datang dan yang sudah selesai
////        List<TicketApply> upcomingTickets = purchasedTicketRepository.findUpcomingTickets(userId);
////        List<TicketApply> completedTickets = purchasedTicketRepository.findCompletedTickets(userId);
////
////        // Mengonversi entity ke DTO
////        List<PurchasedTicketsResponseDto> upcomingEvents = upcomingTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        List<PurchasedTicketsResponseDto> completedEvents = completedTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        // Menggabungkan hasil dan mengembalikannya
////        upcomingEvents.addAll(completedEvents);
////        return upcomingEvents;
////    }
////}
//
////    @Override
////    public List<PurchasedTicketsResponseDto> getPurchasedTickets(Long userId) {
////        List<TicketApply> upcomingTickets = purchasedTicketRepository.findUpcomingTickets(userId);
////        List<TicketApply> completedTickets = purchasedTicketRepository.findCompletedTickets(userId);
////
////        List<PurchasedTicketsResponseDto> upcomingEvents = upcomingTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        List<PurchasedTicketsResponseDto> completedEvents = completedTickets.stream()
////                .map(ticketApply -> PurchasedTicketsResponseDto.fromEntity(ticketApply.getTicketId().getEventId()))
////                .collect(Collectors.toList());
////
////        upcomingEvents.addAll(completedEvents);
////        return upcomingEvents;
////    }
////}