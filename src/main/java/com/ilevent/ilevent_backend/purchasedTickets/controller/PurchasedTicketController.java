//package com.ilevent.ilevent_backend.purchasedTickets.controller;
//
//import com.ilevent.ilevent_backend.purchasedTickets.dto.PurchasedTicketsResponseDto;
//import com.ilevent.ilevent_backend.purchasedTickets.service.PurchasedTicketService;
//import jakarta.annotation.security.RolesAllowed;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collection;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1/purchased")
//public class PurchasedTicketController {
//    private final PurchasedTicketService purchasedTicketService;
//
//    public PurchasedTicketController(PurchasedTicketService purchasedTicketService) {
//        this.purchasedTicketService = purchasedTicketService;
//    }
//    @RolesAllowed("ROLE_PERSONAL")
//    @GetMapping("/purchased")
//    public ResponseEntity<?> getPurchasedTickets(Authentication authentication) {
//        try {
//            // Periksa apakah pengguna memiliki peran ROLE_PERSONAL
//            if (authentication != null && authentication.getAuthorities().stream()
//                    .anyMatch(a -> a.getAuthority().equals("ROLE_PERSONAL"))) {
//                List<PurchasedTicketsResponseDto> tickets = purchasedTicketService.getPurchasedTickets();
//                return ResponseEntity.ok(tickets);
//            } else {
//                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Only personal users can view purchased tickets.");
//            }
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
//}
//
//
////    @GetMapping("/all")
////    public ResponseEntity<List<PurchasedTicketsResponseDto>> getPurchasedTickets(@RequestParam Long userId) {
////        List<PurchasedTicketsResponseDto> tickets = purchasedTicketService.getPurchasedTickets(userId);
////        return ResponseEntity.ok(tickets);
////    }
////}
