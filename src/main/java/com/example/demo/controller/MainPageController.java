package com.example.demo.controller;

import com.example.demo.dto.MainDto;
import com.example.demo.service.MedicationService;
import com.example.demo.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MedicationService medicationService;

    private final S3UploadService s3UploadService;

    @GetMapping("/main")
    public ResponseEntity<?> getMainPage(){
        MainDto mainDto = medicationService.requestMainPage();
        return ResponseEntity.ok(mainDto);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            String fileUrl = s3UploadService.saveFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
