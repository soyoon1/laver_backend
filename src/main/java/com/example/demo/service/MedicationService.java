package com.example.demo.service;

import com.example.demo.domain.MedicationTake;
import com.example.demo.dto.MainDto;
import com.example.demo.dto.MedicationInsertDTO;
import com.example.demo.domain.Medication;
import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.User;
import com.example.demo.dto.MedicationListDto;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.repository.MedicationScheduleRepository;
import com.example.demo.repository.MedicationTakeRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicationService {
    private final MemberRepository memberRepository;
    private final MedicationRepository medicationRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;
    private final MedicationTakeRepository medicationTakeRepository;

    @Transactional
    public void saveMedicationSchedule(MedicationInsertDTO medicationInsertDTO){
        // 사용자 조회
        Optional<User> optionalUser = memberRepository.findById(Integer.valueOf(medicationInsertDTO.getUserId()));
        User findUser = optionalUser.get();

        //약 생성 및 저장
        boolean result = false; // 이미 생성된 약인지 판단.
        Medication needMedication = new Medication();  // 약 스케줄 생성에 필요한 약
        for(Medication medication : medicationRepository.findAll()){  // 모든 약 종류를 찾아줌.
            if(medication.getUser().getId() == Integer.parseInt(medicationInsertDTO.getUserId())) {
                if (medication.getMedicationName().equals(medicationInsertDTO.getMedicationName())) { // 약들 중 지금 추가하려는 약의 이름이 이미 데이터베이스에 저장되어 있다면
                    needMedication = medication;
                    result = true; // 이미 생성된 약이다. 라는 것을 나타냄.
                    break;
                }
            }
        }

        if(!result){  // 이미 생성된 약이 아닐 경우(userId로 분류한 후 데이터베이스에 없는 약인 경우)
            needMedication = Medication.createMedication(findUser, medicationInsertDTO.getMedicationName());
            medicationRepository.save(needMedication);
        }

        //약 스케줄 생성 및 저장
        MedicationSchedule newMedicationSchedule;

            // 동일한 객체가 있는지 검사
        if(medicationScheduleRepository.findByMedicationAndMondayAndTuesdayAndWednesdayAndThursdayAndFridayAndSaturdayAndSundayAndTimeOfDay(needMedication,
                medicationInsertDTO.isMonday(), medicationInsertDTO.isTuesday(), medicationInsertDTO.isWednesday(), medicationInsertDTO.isThursday()
                ,medicationInsertDTO.isFriday(), medicationInsertDTO.isSaturday(), medicationInsertDTO.isSunday()
                , medicationInsertDTO.getTimeOfDay()).isEmpty()){ // 동일한 객체가 없다면
            newMedicationSchedule = MedicationSchedule.createMedicationSchedule(needMedication, medicationInsertDTO.isMonday(), medicationInsertDTO.isTuesday(), medicationInsertDTO.isWednesday(), medicationInsertDTO.isThursday() ,medicationInsertDTO.isFriday(), medicationInsertDTO.isSaturday(), medicationInsertDTO.isSunday(), medicationInsertDTO.getTimeOfDay());

            medicationScheduleRepository.save(newMedicationSchedule); // 새로 객체를 만들어 데이터베이스에 저장해준다.
        }
        newMedicationSchedule = medicationScheduleRepository.findByMedicationAndMondayAndTuesdayAndWednesdayAndThursdayAndFridayAndSaturdayAndSundayAndTimeOfDay(needMedication,medicationInsertDTO.isMonday(), medicationInsertDTO.isTuesday(), medicationInsertDTO.isWednesday(), medicationInsertDTO.isThursday()
                ,medicationInsertDTO.isFriday(), medicationInsertDTO.isSaturday(), medicationInsertDTO.isSunday(), medicationInsertDTO.getTimeOfDay()).get();  // 기존의 데이터를 꺼내온다.

        // 약 스케줄 생성 양방향 관계 시 코드
//        MedicationSchedule newMedicationSchedule = MedicationSchedule.createMedicationSchedule(medicationInsertDTO.getDayOfWeek(), medicationInsertDTO.getTimeOfDay());
//
//
//        // 약 생성 및 저장
//        Medication medication = Medication.createMedication(findUser, medicationInsertDTO.getMedicationName(), newMedicationSchedule);
//        medicationRepository.save(medication);

    }
    @Transactional
    public Medication findOne(Integer id){return medicationRepository.findById(id).get();}


    // 오전, 오후에 해당하는 약 스케줄
    // public


    @Transactional
    public Medication findMedicationByUserIdAndMedicationId(int userId, int medicationId){
        // userId와 medicationId를 기반으로 Medication을 찾아서 반환합니다.
        return medicationRepository.findByUser_IdAndId(userId, medicationId);
    }

    @Transactional
    public MainDto requestMainPage() {
        List<MedicationListDto> medicationList = new ArrayList<>();

        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다.")); // 확인 필요
        Date createdDate = user.getCreatedDate();

        for (Medication medication : medicationRepository.findByUser(user)) {
            int medicationId = medication.getId();
            String medicationName = medication.getMedicationName();
            boolean didTake = false;

            for (MedicationSchedule schedule : medicationScheduleRepository.findByMedication(medication)) {
                LocalTime timeOfDay = schedule.getTimeOfDay();

                // 현재 요일을 가져옴. 원하는 형식으로 요일을 포맷팅
                LocalDateTime now = LocalDateTime.now();
                String formattedDay = now.format(DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko")));

                boolean isScheduleDay = false;
                switch (formattedDay) {    // 오늘 요일이 무엇인지에 따라 오늘 요일이 약을 먹어야 하는 요일인지 알 수 있음.
                    case "월":
                        isScheduleDay = schedule.isMonday();
                        break;

                    case "화":
                        isScheduleDay = schedule.isTuesday();
                        break;

                    case "수":
                        isScheduleDay = schedule.isWednesday();
                        break;

                    case "목":
                        isScheduleDay = schedule.isThursday();
                        break;

                    case "금":
                        isScheduleDay = schedule.isFriday();
                        break;

                    case "토":
                        isScheduleDay = schedule.isSaturday();
                        break;

                    case "일":
                        isScheduleDay = schedule.isSunday();
                        break;

                }

                if (isScheduleDay) {
                    for (MedicationTake medicationTake : medicationTakeRepository.findByMedicationSchedule(schedule)) {// 스케줄로 일단 다 찾은 다음 현재 날짜와 비교해서 있으면 true, 없으면 false
                        LocalDate storedDate = medicationTake.getTimeOfTaking().toLocalDate();
                        LocalDate currentDate = LocalDateTime.now().toLocalDate();

                        didTake = storedDate.isEqual(currentDate);
                    }
                    // 이제 medicationList에 추가해주면 됨.
                    MedicationListDto medicationListDto = MedicationListDto.builder()
                            .medicationId(medicationId)
                            .medicationName(medicationName)
                            .timeOfDay(timeOfDay)
                            .didTake(didTake)
                            .build();
                    medicationList.add(medicationListDto);

                }
            }

        }


        MainDto mainDto = MainDto.builder()
                .createdDate(createdDate)
                .medicationList(medicationList)
                .build();

        return mainDto;

    }


}