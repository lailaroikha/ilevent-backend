package com.ilevent.ilevent_backend.pointsHistory.Service.impl;

import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.pointsHistory.entity.PointsHistory;
import com.ilevent.ilevent_backend.pointsHistory.repository.PointHistoryRepository;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PointHistoryServiceImpl implements PointHistoryService {
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public PointHistoryServiceImpl(UserRepository userRepository, PointHistoryRepository pointHistoryRepository) {
        this.userRepository = userRepository;
        this.pointHistoryRepository = pointHistoryRepository;
    }


    @Override
    public PointsHistory addPointsHistory(Long userId, Integer points, String type) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setUser(user);
        pointsHistory.setPoints(points);
        pointsHistory.setType(type);
        pointsHistory.setCreatedAt(Instant.now());
        pointsHistory.setUpdateAt(Instant.now());
        PointsHistory savedHistory = pointHistoryRepository.save(pointsHistory);

        return savedHistory;
    }

    @Override
    public Integer getTotalPoints(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getTotalPoints();
    }

//    @Override
//    public PointHistoryResponseDto addPointsHistory(PointHistoryRequestDto dto) {
//        Optional<Users> user = userRepository.findById(dto.getUserId());
//        if (user.isEmpty()) {
//            throw new IllegalArgumentException("User not found");
//        }
//        PointsHistory pointsHistory = new PointsHistory();
//        pointsHistory.setUser(user.get());
//        pointsHistory.setPoints(dto.getPoints());
//        pointsHistory.setType(dto.getType());
//        pointsHistory.setCreatedAt(Instant.now());
//        pointsHistory.setUpdateAt(Instant.now());
//        pointHistoryRepository.save(pointsHistory);
//
//        return PointHistoryResponseDto.fromEntity(pointsHistory);
//    }
}
