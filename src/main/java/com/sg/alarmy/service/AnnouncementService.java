package com.sg.alarmy.service;

import com.sg.alarmy.dto.request.AnnouncementDto;
import com.sg.alarmy.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public List<AnnouncementDto> findAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(AnnouncementDto::new)
                .collect(Collectors.toList());
    }
}