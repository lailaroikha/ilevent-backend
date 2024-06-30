package com.ilevent.ilevent_backend.users.service.impl;

import com.ilevent.ilevent_backend.exceptions.ApplicationException;
import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.users.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Users register(RegisterRequestDto user) {
        Users newUser = user.toEntity();
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(password);
        // Set role
        newUser.setIsOrganizer(user.getIsOrganizer());
        // Set timestamps
        newUser.setCreatedAt(Instant.now());
        newUser.setUpdateAt(Instant.now());

        return userRepository.save(newUser);

    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ApplicationException("User not found"));
    }

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ApplicationException("User not found"));
    }
    @Override
    public List<Users> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public Users profile() {
        return null;
    }


}
