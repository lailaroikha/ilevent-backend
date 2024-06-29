package com.ilevent.ilevent_backend.users.service;

import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.entity.Users;

import java.util.List;


public interface UserService {
    Users register(RegisterRequestDto req);
    Users findByEmail(String email);

    List<Users> findAll();
    Users profile();

}
