package com.ilevent.ilevent_backend.report.service.impl;



import com.ilevent.ilevent_backend.report.dto.RevenueByEventDTO;
import com.ilevent.ilevent_backend.report.service.ReportService;
import com.ilevent.ilevent_backend.transaction.entity.Transaction;
import com.ilevent.ilevent_backend.transaction.repository.TransactionRepository;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ReportServiceImpl implements ReportService {
    private final TransactionRepository transactionRepository;
    private final UserRepository usersRepository;

    public ReportServiceImpl(TransactionRepository transactionRepository, UserRepository usersRepository) {
        this.transactionRepository = transactionRepository;
        this.usersRepository = usersRepository;
    }
    @Override
    public RevenueByEventDTO getRevenueByHourToday(Authentication authentication) {

        String email = authentication.getName();
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!Boolean.TRUE.equals(user.getOrganizer())) {
            throw new RuntimeException("User is not an organizer");
        }
        LocalDate today = LocalDate.now();
        List<Transaction> transactions = transactionRepository.findAllByOrganizerAndTransactionDateBetween(user, today, today);

        Map<Integer, BigDecimal> revenuePerHour = new HashMap<>();
        BigDecimal totalRevenue = BigDecimal.ZERO;

        // Mengelompokkan transaksi per jam
        for (Transaction transaction : transactions) {
            Instant timestamp = transaction.getCreatedAt(); // atau getUpdatedAt(), pilih yang sesuai
            LocalDateTime localDateTime = LocalDateTime.ofInstant(timestamp, ZoneId.systemDefault());
            int hour = localDateTime.getHour();
            BigDecimal amount = transaction.getAmount();
            revenuePerHour.merge(hour, amount, BigDecimal::add);
            totalRevenue = totalRevenue.add(amount);
        }

        RevenueByEventDTO result = new RevenueByEventDTO();
        List<RevenueByEventDTO.TimeSegmentRevenueDTO> hourlyRevenues = new ArrayList<>();

        // Membuat segmen untuk setiap jam
        IntStream.rangeClosed(0, 23).forEach(hour -> {
            RevenueByEventDTO.TimeSegmentRevenueDTO hourlyRevenue = new RevenueByEventDTO.TimeSegmentRevenueDTO();
            hourlyRevenue.setSegmentName("Hour " + hour);
            hourlyRevenue.setRevenue(revenuePerHour.getOrDefault(hour, BigDecimal.ZERO));
            hourlyRevenues.add(hourlyRevenue);
        });

        result.setTotalRevenue(totalRevenue);
        result.setTimeSegments(hourlyRevenues);
        return result;
    }}
//    @Override
//    public RevenueByEventDTO getRevenueByHourToday(Authentication authentication) {
//        String email = authentication.getName();
//        Users user = usersRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        if (!Boolean.TRUE.equals(user.getOrganizer())) {
//            throw new RuntimeException("User is not an organizer");
//        }
//        LocalDate today = LocalDate.now();
//        List<Transaction> transactions = transactionRepository.findAllByOrganizerAndTransactionDateBetween(user, today, today);
//
//        Map<Integer, BigDecimal> revenuePerHour = new HashMap<>();
//        BigDecimal totalRevenue = BigDecimal.ZERO;
//
//        // Mengelompokkan transaksi per jam
//        for (Transaction transaction : transactions) {
//            Instant timestamp = Instant.from(transaction.getCreatedAt()); // atau getUpdatedAt(), pilih yang sesuai
//            int hour = timestamp.getHour();
//            BigDecimal amount = transaction.getAmount();
//            revenuePerHour.merge(hour, amount, BigDecimal::add);
//            totalRevenue = totalRevenue.add(amount);
//        }
//
//        RevenueByEventDTO result = new RevenueByEventDTO();
//        List<RevenueByEventDTO.TimeSegmentRevenueDTO> hourlyRevenues = new ArrayList<>();
//
//        // Membuat segmen untuk setiap jam
//        IntStream.rangeClosed(0, 23).forEach(hour -> {
//            RevenueByEventDTO.TimeSegmentRevenueDTO hourlyRevenue = new RevenueByEventDTO.TimeSegmentRevenueDTO();
//            hourlyRevenue.setSegmentName("Hour " + hour);
//            hourlyRevenue.setRevenue(revenuePerHour.getOrDefault(hour, BigDecimal.ZERO));
//            hourlyRevenues.add(hourlyRevenue);
//        });
//
//        result.setTotalRevenue(totalRevenue);
//        result.setTimeSegments(hourlyRevenues);
//        return result;
//    }}

//    @Override
//    public RevenueByEventDTO getRevenueByHourToday(Authentication authentication) {
//        String email = authentication.getName();
//
//        Users user = usersRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        if (!Boolean.TRUE.equals(user.getOrganizer())) {
//            throw new RuntimeException("User is not an organizer");
//        }
//
//        LocalDate today = LocalDate.now();
//
//        List<Transaction> transactions = transactionRepository.findAllByOrganizerAndTransactionDateBetween(user, today, today);
//
//        Map<Integer, BigDecimal> revenueMap = transactions.stream()
//                .collect(Collectors.groupingBy(
//                        t -> LocalDateTime.of(t.getTransactionDate(), LocalTime.MIDNIGHT).getHour(),
//                        Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)
//                ));
//
//        RevenueByEventDTO result = new RevenueByEventDTO();
//        List<RevenueByEventDTO.TimeSegmentRevenueDTO> hourlyRevenues = new ArrayList<>();
//
//        final BigDecimal[] totalRevenue = {BigDecimal.ZERO};
//
//        // Inisialisasi semua jam dari 0 hingga 23 dengan pendapatan nol
//        IntStream.rangeClosed(0, 23).forEach(hour -> {
//            BigDecimal revenue = revenueMap.getOrDefault(hour, BigDecimal.ZERO);
//            totalRevenue[0] = totalRevenue[0].add(revenue);
//
//            RevenueByEventDTO.TimeSegmentRevenueDTO hourlyRevenue = new RevenueByEventDTO.TimeSegmentRevenueDTO();
//            hourlyRevenue.setSegmentName("Hour " + hour);
//            hourlyRevenue.setRevenue(revenue);
//            hourlyRevenues.add(hourlyRevenue);
//        });
//
//        result.setTotalRevenue(totalRevenue[0]);
//        result.setTimeSegments(hourlyRevenues);
//
//        return result;
//    }
//}