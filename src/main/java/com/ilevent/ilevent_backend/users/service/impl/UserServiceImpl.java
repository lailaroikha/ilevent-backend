package com.ilevent.ilevent_backend.users.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ilevent.ilevent_backend.exceptions.ApplicationException;
import com.ilevent.ilevent_backend.pointsHistory.Service.PointHistoryService;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.repository.ReferralRepository;
import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.dto.RegisterResponseDto;
import com.ilevent.ilevent_backend.users.dto.UpdateProfileDto;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.users.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReferralRepository referralRepository;
    private final PointHistoryService pointHistoryService;
    private final Cloudinary cloudinary;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ReferralRepository referralRepository, PointHistoryService pointHistoryService, Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.referralRepository = referralRepository;
        this.pointHistoryService = pointHistoryService;

        this.cloudinary = cloudinary;
    }

    @Transactional
    @Override
    public RegisterResponseDto register(RegisterRequestDto user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already exist");
        }

        Users newUser = user.toEntity(); // Convert DTO to entity
        var password = passwordEncoder.encode(user.getPassword());
        newUser.setPassword(password);

        // Generate referral code using the name from the new user
        String referralCode = generateReferralCode(newUser.getName());
        newUser.setReferralCode(referralCode);

        Users savedUser = userRepository.save(newUser);
        if (user.getReferralCode() != null) {
            Users referrer = userRepository.findByReferralCode(user.getReferralCode())
                    .orElseThrow(() -> new ApplicationException("Referral code not found"));
            if (referrer != null) {
                Referral referral = new Referral();
                referral.setUser(savedUser);
                referral.setReferredUserId(referrer);
                Instant now = Instant.now();
                referral.setCreatedAt(now);
                referral.setUpdatedAt(now);
                referralRepository.save(referral);
                //Add points to the referring user
                pointHistoryService.addPointsHistory(referrer.getId(), 10000, "REFERRAL");
            }

        }

        return RegisterResponseDto.fromEntity(savedUser);
    }

    private String generateReferralCode(String name) {
        final String LOWERCASE_AND_DIGITS = "abcdefghijklmnopqrstuvwxyz0123456789";
        final int ADDITIONAL_CODE_LENGTH = 4;
        final SecureRandom secureRandom = new SecureRandom();
        // Generate the first 2 characters as uppercase letters
        String namePart;
        if (name.length() >= 2) {
            namePart = name.substring(0, 2).toUpperCase();
        } else {
            namePart = (name + "X").substring(0, 2).toUpperCase();
        }
        // Generate the next 4 characters as lowercase letters or digits
        StringBuilder code = new StringBuilder(ADDITIONAL_CODE_LENGTH);
        for (int i = 0; i < ADDITIONAL_CODE_LENGTH; i++) {
            code.append(LOWERCASE_AND_DIGITS.charAt(secureRandom.nextInt(LOWERCASE_AND_DIGITS.length())));
        }
        return namePart + code;
    }


    @Override
    public Users getUserById(Long Id) {
        return userRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + Id));
    }


    @SuppressWarnings("unchecked")
    @Override
    public String uploadProfilePicture(String username, MultipartFile file) {
        Users user = userRepository.findByUsername(username);
        if (user != null && !file.isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "profile_pictures"));
                String imageUrl = (String) uploadResult.get("url");
                user.setPicture(imageUrl);
                userRepository.save(user);
                return imageUrl;
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }
        return null;
    }

    @Transactional
    @Override
    public Users updateProfile(String email, UpdateProfileDto updateProfileDto, MultipartFile profilePicture) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if (updateProfileDto.getName() != null && !updateProfileDto.getName().isEmpty()) {
            user.setName(updateProfileDto.getName());
        }

        if (updateProfileDto.getPhone() != null && !updateProfileDto.getPhone().isEmpty()) {
            user.setPhone(updateProfileDto.getPhone());
        }

        if (profilePicture != null && !profilePicture.isEmpty()) {
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(
                        profilePicture.getBytes(),
                        ObjectUtils.asMap("folder", "profile_pictures")
                );
                String imageUrl = (String) uploadResult.get("url");
                user.setPicture(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload profile picture", e);
            }
        }

        return userRepository.save(user);
    }
}
