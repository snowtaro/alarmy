package com.sg.alarmy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // src/main/resources/templates/login.html 을 찾아갑니다.
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/login"; // 루트 접속 시 로그인 페이지로 이동
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main";
    }
}