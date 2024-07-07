package com.ilevent.ilevent_backend.pointsHistory.Service.impl;

import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryRequestDto;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryResponseDto;
import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import com.ilevent.ilevent_backend.pointsHistory.repository.PointHistoryRepository;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;
    private final UserRepository userRepository;

    public PointHistoryServiceImpl(PointHistoryRepository pointHistoryRepository, UserRepository userRepository) {
        this.pointHistoryRepository = pointHistoryRepository;
        this.userRepository = userRepository;
    }


    @Override
    public PointHistoryResponseDto addPointsHistory(PointHistoryRequestDto dto) {
        Optional<Users> user = userRepository.findById(dto.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setUser(user.get());
        pointsHistory.setPoints(dto.getPoints());
        pointsHistory.setType(dto.getType());
        pointsHistory.setCreatedAt(Instant.now());
        pointsHistory.setUpdateAt(Instant.now());
        pointHistoryRepository.save(pointsHistory);

        return PointHistoryResponseDto.fromEntity(pointsHistory);
    }

    @Override
    public Integer getTotalPoints(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getTotalPoints();
    }
}
