package com.ilevent.ilevent_backend.users.service;

import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.entity.Users;

import java.util.List;


public interface UserService {
    Users register(RegisterRequestDto req);
    Users findByEmail(String email);
    Users findById(Long id);
    List<Users> findAll();
    void deleteById(Long id);
    Users profile();

}
