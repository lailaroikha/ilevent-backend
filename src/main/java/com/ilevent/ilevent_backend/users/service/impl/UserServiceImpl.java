package com.ilevent.ilevent_backend.users.service.impl;

import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.users.service.UserService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users register(RegisterRequestDto req) {
        Users newUser = req.toEntity();
        var password = passwordEncoder.encode(req.getPassword());
        newUser.setPassword(password);
        // Set role
        newUser.setIsOrganizer(req.getIsOrganizer());

        // Set timestamps
        newUser.setCreatedAt(Instant.now());
        newUser.setUpdateAt(Instant.now());

        return userRepository.save(newUser);

    }

    @Override
    public Users findByEmail(String email) {
        return null;
    }

    @Override
    public List<Users> findAll() {
        return List.of();
    }

    @Override
    public Users profile() {
        return null;
    }


}
