package com.sg.alarmy.controller;

import com.sg.alarmy.dto.request.AnnouncementDto;
import com.sg.alarmy.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @GetMapping
    public ResponseEntity<List<AnnouncementDto>> getAnnouncements() {
        List<AnnouncementDto> list = announcementService.findAllAnnouncements();
        return ResponseEntity.ok(list);
    }
}