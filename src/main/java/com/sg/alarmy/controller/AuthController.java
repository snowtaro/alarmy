package com.sg.alarmy.controller;

import com.sg.alarmy.dto.LoginDto;
import com.sg.alarmy.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginDto.Response> login(@RequestBody LoginDto.Request request) {
        LoginDto.Response response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}