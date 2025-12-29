package com.sg.alarmy.dto;

import lombok.Getter;
import lombok.Setter;

public class LoginDto {
    @Getter @Setter
    public static class Request {
        private String email;
        private String password;
    }

    @Getter @Setter
    public static class Response {
        private String token;
        private String nickname;

        public Response(String token, String nickname) {
            this.token = token;
            this.nickname = nickname;
        }
    }
}