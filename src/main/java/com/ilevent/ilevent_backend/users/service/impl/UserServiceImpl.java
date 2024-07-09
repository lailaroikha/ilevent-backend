package com.ilevent.ilevent_backend.users.service.impl;

import com.ilevent.ilevent_backend.exceptions.ApplicationException;
import com.ilevent.ilevent_backend.referral.entity.Referral;
import com.ilevent.ilevent_backend.referral.repository.ReferralRepository;
import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.dto.RegisterResponseDto;
import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.users.service.UserService;
import com.ilevent.ilevent_backend.utils.ReferralCodeGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReferralRepository referralRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ReferralRepository referralRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.referralRepository = referralRepository;
    }

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
//        Users savedUser = new Users();

        //check dto if referral code is provided
        if (user.getReferralCode() != null) {
            Users referrer = userRepository.findByReferralCode(user.getReferralCode())
                    .orElseThrow(() -> new ApplicationException("Referral code not found"));
            if (referrer != null) {
                Referral referral = new Referral();
//                referral.setUser(newUser);
                referral.setUser(savedUser);
                referral.setReferredUserId(referrer);

                // Set properti required yang tidak boleh null
                Instant now = Instant.now();
                referral.setCreatedAt(now);
                referral.setUpdatedAt(now);

                referralRepository.save(referral);
            }
        }

        return RegisterResponseDto.fromEntity(savedUser);
    }
    private RegisterResponseDto toResponseDto(Users users) {
        RegisterResponseDto dto = new RegisterResponseDto();
        dto.setName(users.getName());
        dto.setIsOrganizer(users.getIsOrganizer());
        dto.setReferralCode(users.getReferralCode());
        return dto;
    }


    private String generateReferralCode(String name) {
        final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String LOWERCASE_AND_DIGITS = "abcdefghijklmnopqrstuvwxyz0123456789";
        final int UPPERCASE_CODE_LENGTH = 2;
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
//        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
//        final int ADDITIONAL_CODE_LENGTH = 4;
//        final SecureRandom secureRandom = new SecureRandom();
//
//        // Ambil 2 karakter pertama dari name dan pastikan panjangnya tepat 2 karakter
//        String namePart;
//        if (name.length() >= 2) {
//            namePart = name.substring(0, 2).toUpperCase();
//        } else {
//            namePart = (name + "X").substring(0, 2).toUpperCase();
//        }
//
//        // Buat sisa kode dengan 4 karakter bebas
//        StringBuilder code = new StringBuilder(ADDITIONAL_CODE_LENGTH);
//        for (int i = 0; i < ADDITIONAL_CODE_LENGTH; i++) {
//            code.append(CHARACTERS.charAt(secureRandom.nextInt(CHARACTERS.length())));
//        }

        return namePart + code.toString();
    }

    @Override
    public RegisterResponseDto findByEmail(String email) {
        return null;
    }

    @Override
    public Users getUserById(Long Id) {
        return userRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + Id));
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
