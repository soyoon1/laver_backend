package com.example.demo.controller;

import com.example.demo.domain.Medication;
import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.User;
import com.example.demo.dto.MedicationAddRequestDto;
import com.example.demo.dto.MyPageInfoDto;
import com.example.demo.dto.PushSettingsDto;
import com.example.demo.dto.UserSettingsDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.repository.MedicationScheduleRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MedicationService;
import com.example.demo.service.UserService;
import com.google.api.gax.rpc.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MemberRepository memberRepository;

    private final UserService userService;
    private final MedicationRepository medicationRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;
    private final MedicationService medicationService;

    @GetMapping("/mypage")
    public ResponseEntity<?> getUserMyPage(){
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        MyPageInfoDto myPageInfo = userService.getMyPageInfoByUserId(user.getId());
        return ResponseEntity.ok(myPageInfo);

    }

    @PostMapping("/medication-add")
    public ResponseEntity<?> addMedications(@RequestBody List<MedicationAddRequestDto> requestDtoList){
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        for(MedicationAddRequestDto requestDto : requestDtoList){
            Medication medication = Medication.createMedication(user, requestDto.getMedicationName());
            medicationRepository.save(medication);
            boolean monday = requestDto.isMonday();
            boolean tuesday = requestDto.isTuesday();
            boolean wednesday = requestDto.isWednesday();
            boolean thursday = requestDto.isThursday();
            boolean friday = requestDto.isFriday();
            boolean saturday = requestDto.isSaturday();
            boolean sunday = requestDto.isSunday();
            LocalTime timeOfDay = requestDto.getTimeOfDay();

            MedicationSchedule schedule = MedicationSchedule.createMedicationSchedule(medication, monday, tuesday, wednesday, thursday, friday, saturday, sunday, timeOfDay);
            medicationScheduleRepository.save(schedule);
            user.getMedications().add(medication);
            medication.getMedicationSchedules().add(schedule);
        }

        userService.saveUser(user);


        return ResponseEntity.ok("Medications added successfully");
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message){
            super(message);
        }
    }

    @GetMapping("/{medicationId}")
    public ResponseEntity<?> getMedicationDetail(@PathVariable int medicationId){
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        Medication medication = medicationService.findMedicationByUserIdAndMedicationId(user.getId(), medicationId);

        if(medication == null){
            throw new NotFoundException("Medication not found");
        }

        MedicationAddRequestDto dto = new MedicationAddRequestDto();
        dto.setMedicationName(medication.getMedicationName());

        MedicationSchedule schedule = medication.getMedicationSchedules().get(0); // 복용스케줄이 하나라고 가정
        dto.setMonday(schedule.isMonday());
        dto.setTuesday(schedule.isTuesday());
        dto.setWednesday(schedule.isWednesday());
        dto.setThursday(schedule.isThursday());
        dto.setFriday(schedule.isFriday());
        dto.setSaturday(schedule.isSaturday());
        dto.setSunday(schedule.isSunday());
        dto.setTimeOfDay(schedule.getTimeOfDay());

        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{medicationId}")
    public ResponseEntity<?> updateMedication(@PathVariable int medicationId, @RequestBody MedicationAddRequestDto requestDto) {
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        Medication medication = medicationService.findMedicationByUserIdAndMedicationId(user.getId(), medicationId);

        if (medication == null) {
            throw new NotFoundException("Medication not found");
        }

        // Update medication details
        medication.setMedicationName(requestDto.getMedicationName());

        MedicationSchedule schedule = medication.getMedicationSchedules().get(0); // 복용스케줄이 하나라고 가정
        schedule.setMonday(requestDto.isMonday());
        schedule.setTuesday(requestDto.isTuesday());
        schedule.setWednesday(requestDto.isWednesday());
        schedule.setThursday(requestDto.isThursday());
        schedule.setFriday(requestDto.isFriday());
        schedule.setSaturday(requestDto.isSaturday());
        schedule.setSunday(requestDto.isSunday());
        schedule.setTimeOfDay(requestDto.getTimeOfDay());

        medicationRepository.save(medication); // 약 정보 저장
        medicationScheduleRepository.save(schedule); // 스케줄 정보 저장

        return ResponseEntity.ok("Medication updated successfully");
    }

    @DeleteMapping("/{medicationId}")
    public ResponseEntity<?> deleteMedication(@PathVariable int medicationId) {
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        Medication medication = medicationService.findMedicationByUserIdAndMedicationId(user.getId(), medicationId);

        if (medication == null) {
            throw new NotFoundException("Medication not found");
        }

        List<MedicationSchedule> schedules = medication.getMedicationSchedules();  // 여기에서는 약 스케줄이 약마다 여러 개 있을 것이라고 가정하고 있다.
        for (MedicationSchedule schedule : schedules) {
            medicationScheduleRepository.delete(schedule); // 약 스케줄 삭제
        }

        medicationRepository.delete(medication); // 약 정보 삭제

        return ResponseEntity.ok("Medication and schedules deleted successfully");
    }

    @GetMapping("/settings")
    public ResponseEntity<?> getUserSettings() {
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        UserSettingsDto settingsDto = new UserSettingsDto();
        settingsDto.setName(user.getName());
        settingsDto.setSentence(user.getSentence());
        settingsDto.setNickname(user.getNickname());
        settingsDto.setAlarm(user.isAlarm());

        return ResponseEntity.ok(settingsDto);
    }

    @PutMapping("/settings/sentence")  // dto를 사용하지 않음.
    public ResponseEntity<?> updateUserSentence(@RequestBody String sentence) {
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        user.setSentence(sentence);
        userService.saveUser(user);

        return ResponseEntity.ok("User sentence updated successfully");
    }

    @PutMapping("/settings/push")
    public ResponseEntity<?> updatePushSettings(@RequestBody PushSettingsDto requestDto){
        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(()-> new RuntimeException("로그인 유저 정보가 없습니다."));

        userService.updateAlarmSetting(user.getId(), requestDto.isAlarm());
        return ResponseEntity.ok("Alarm setting updated successfully");
    }


}
