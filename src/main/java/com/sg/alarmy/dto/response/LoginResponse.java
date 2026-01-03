package com.sg.alarmy.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginResponse {
    private String token;
    private long expiration;

    public LoginResponse(String token, long expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
