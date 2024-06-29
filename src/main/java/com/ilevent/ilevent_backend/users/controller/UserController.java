package com.ilevent.ilevent_backend.users.controller;

import com.ilevent.ilevent_backend.responses.Response;
import com.ilevent.ilevent_backend.users.dto.RegisterRequestDto;
import com.ilevent.ilevent_backend.users.service.UserService;
import com.ilevent.ilevent_backend.auth.helper.Claims;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
@Validated
@Log
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return Response.success("User registered successfully", userService.register(registerRequestDto));
    }
    @GetMapping("/profile")
    public ResponseEntity<?> profile() {
        var claims = Claims.getClaimsFromJwt();
        var email = (String) claims.get("sub");
        log.info("Claims are: " + claims.toString());
        log.info("User profile requested for user: " + email);
        return Response.success("User profile", userService.findByEmail(email));
}
}
