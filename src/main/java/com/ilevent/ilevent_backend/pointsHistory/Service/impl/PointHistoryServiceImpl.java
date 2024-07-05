package com.ilevent.ilevent_backend.pointsHistory.Service.impl;

import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryRequestDto;
import com.ilevent.ilevent_backend.pointsHistory.dto.PointHistoryResponseDto;
import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import com.ilevent.ilevent_backend.pointsHistory.repository.PointHistoryRepository;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    private final PointHistoryRepository pointsHistoryRepository;
    private final UserRepository userRepository;

    public PointHistoryServiceImpl(PointHistoryRepository pointsHistoryRepository, UserRepository userRepository) {
        this.pointsHistoryRepository = pointsHistoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PointHistoryResponseDto createPointsHistory(PointHistoryRequestDto pointHistoryRequestDto) {
        Optional<Users> userOptional = userRepository.findById(pointHistoryRequestDto.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }
        Users user = userOptional.get();
        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPoints(pointHistoryRequestDto.getPoints());
        pointsHistory.setType(pointHistoryRequestDto.getType());
        pointsHistory.setCreatedAt(pointHistoryRequestDto.getCreatedAt());
        pointsHistory.setUpdateAt(pointHistoryRequestDto.getUpdateAt());
        pointsHistoryRepository.save(pointsHistory);

        return PointHistoryResponseDto.fromEntity(pointsHistory);
    }

    @Override
    public List<PointHistoryResponseDto> getPointsHistoryByUserId(Long userId) {
        List<PointsHistory> pointsHistories = pointsHistoryRepository.findByUserId(userId);
        return pointsHistories.stream()
                .map(PointHistoryResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
