package com.ilevent.ilevent_backend.users.service;

import com.ilevent.ilevent_backend.users.dto.ProfileResponseDto;
import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.dto.RegisterResponseDto;
import com.ilevent.ilevent_backend.users.dto.UpdateProfileDto;
import com.ilevent.ilevent_backend.users.entity.Users;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    RegisterResponseDto register(RegisterRequestDto registerRequestDto);
//    RegisterResponseDto findByEmail(String email);
    Users getUserById(Long id);
    String uploadProfilePicture(String username, MultipartFile file);
    Users updateProfile(String email, UpdateProfileDto updateProfileDto,  MultipartFile profilePicture);
    ProfileResponseDto getProfile(Long id); // profile
}
