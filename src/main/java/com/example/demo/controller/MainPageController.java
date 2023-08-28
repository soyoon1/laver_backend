package com.example.demo.controller;

import com.example.demo.dto.MainDto;
import com.example.demo.service.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MedicationService medicationService;

    @GetMapping("/main")
    public ResponseEntity<?> getMainPage(){
        MainDto mainDto = medicationService.requestMainPage();
        return ResponseEntity.ok(mainDto);
    }

}
