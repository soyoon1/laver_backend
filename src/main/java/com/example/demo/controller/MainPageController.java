package com.example.demo.controller;

import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.MedicationTake;
import com.example.demo.dto.MainDto;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.repository.MedicationScheduleRepository;
import com.example.demo.repository.MedicationTakeRepository;
import com.example.demo.service.MedicationService;
import com.example.demo.service.S3UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MedicationService medicationService;
    private final MedicationScheduleRepository medicationScheduleRepository;
    private final MedicationRepository medicationRepository;
    private final MedicationTakeRepository medicationTakeRepository;

    private final S3UploadService s3UploadService;

    @GetMapping("/main")
    public ResponseEntity<?> getMainPage(){
        MainDto mainDto = medicationService.requestMainPage();
        return ResponseEntity.ok(mainDto);
    }

    @PostMapping("/upload") // 테스트 코드
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try {
            String fileUrl = s3UploadService.saveFile(file);
            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/main/medication-add")
    public ResponseEntity<String> uploadMedicationImg(@RequestPart("file") MultipartFile file,
                                                      @RequestPart("medicationId") String medicationId){
        try {
            // medicationSchedule을 medicationId로 찾고(medication 당 medicationSchedule이 현재 1개밖에 안 만들어지니까 이렇게 해도 됨. 나중에 여러 개로 바뀌면 사용자에게 약 데이터를 넘겨줄 때 어떤 schedule의 약을 먹는지 받으면 됨),
            // medicationSchedule로 medicationTake를 만들면 됨. img에는 fileUrl을 넣어주고, timeOfTaking은 new Date()를 해주면 되고 Schedule_id에는 찾은 schedule의 scheduleId를 넣으면 됨.

            MedicationSchedule medicationSchedule = medicationScheduleRepository.findByMedication(medicationRepository.findById(Integer.valueOf(medicationId)).get()).get(0); // 일단 첫번째를 찾아옴. 약 당 스케줄이 여러 개라면 나중에 이걸로 다 찾아와서 어떤 방법으로든 찾으면 될

            String fileUrl = s3UploadService.saveFile(file);

            MedicationTake medicationTake = MedicationTake.builder()
                    .medicationSchedule(medicationSchedule)
                    .timeOfTaking(LocalDateTime.now())
                    .img(fileUrl)
                    .build();

            medicationTakeRepository.save(medicationTake); // DB에 저장

            return ResponseEntity.ok(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}