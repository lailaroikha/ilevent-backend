package com.ilevent.ilevent_backend.users.service;

import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.dto.RegisterResponseDto;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.security.core.userdetails.User;

import java.util.List;


public interface UserService {
    RegisterResponseDto register(RegisterRequestDto req);
    RegisterResponseDto findByEmail(String email);
    Users findById(Long id);
    List<Users> findAll();
    void deleteById(Long id);
    Users profile();

}
