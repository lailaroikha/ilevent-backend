package com.ilevent.ilevent_backend.users.service.impl;

import com.ilevent.ilevent_backend.users.entity.Users;
import com.ilevent.ilevent_backend.users.repository.UserRepository;
import com.ilevent.ilevent_backend.users.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users register(Users req) {
        return userRepository.save(req);
    }
    @Override
    public Users profile(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not Found")) ;
    }

}
