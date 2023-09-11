package com.example.demo.service;

import com.example.demo.domain.MedicationTake;
import com.example.demo.dto.*;
import com.example.demo.domain.Medication;
import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.repository.MedicationScheduleRepository;
import com.example.demo.repository.MedicationTakeRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    }
    @Transactional
    public Medication findOne(Integer id){return medicationRepository.findById(id).get();}


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

    // 매일 MedicationTake 테이블을 채움. 달력에 안 먹은 약도 표시하기 위함. 당일 medicationSchedule 테이블에 있는 약들 중 객체가 만들어지지 않은 약스케줄들을 img를 null로 객체를 만들어 저장시켜 줌.
    @Transactional
    @Scheduled(cron = "59 59 23 * * ?") // 매일 자정이 되기 1초 전에 실행, 오늘 먹지 못한 약들을 MedicationTake에 객체를 만들어 저장시켜줌.
    public void makeMedicationTakeData(){
        // 오늘이 무슨 요일인지 구함
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek(); // 1: 월요일, 2: 화요일 ... 7: 일요일
        // 그 요일의 값이 true인 데이터들을 medicationSchedule 테이블에서 다 구해옴.
        List<MedicationSchedule> medicationScheduleList = new ArrayList<>();
        switch (dayOfWeek){
            case MONDAY:
                medicationScheduleList = medicationScheduleRepository.findByMonday(true);
                break;

            case TUESDAY:
                medicationScheduleList = medicationScheduleRepository.findByTuesday(true);
                break;

            case WEDNESDAY:
                medicationScheduleList = medicationScheduleRepository.findByWednesday(true);
                break;

            case THURSDAY:
                medicationScheduleList = medicationScheduleRepository.findByThursday(true);
                break;

            case FRIDAY:
                medicationScheduleList = medicationScheduleRepository.findByFriday(true);
                break;

            case SATURDAY:
                medicationScheduleList = medicationScheduleRepository.findBySaturday(true);
                break;

            case SUNDAY:
                medicationScheduleList = medicationScheduleRepository.findBySunday(true);
                break;
        }
        // timeOfTaking이 오늘 날짜에 해당하는 medicationTake 데이터를 다 구해옴. 시간은 상관없음.
//        LocalDate today = LocalDate.now();
//        List<MedicationTake> medicationTakeList = medicationTakeRepository.findByTimeOfTaking(today);

        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        List<MedicationTake> medicationTakeList = medicationTakeRepository.findByTimeOfTakingBetween(startOfDay, endOfDay);

//        // 구해진 medicationSchedule 데이터들 중 구해진 medicationTake 데이터와 비교해 데이터가 없는 값들을 찾음. (medicationTake의 scheduleId를 보고 비교하면 될 것)
//        List<MedicationSchedule> missingScheduleList = new ArrayList<>();
//        for(MedicationSchedule schedule: medicationScheduleList){
//            boolean found = false;
//            for(MedicationTake take: medicationTakeList){
//                if(schedule.equals(take.getMedicationSchedule())){
//                    found = true;
//                    break;
//                }
//            }
//            if(!found){
//                missingScheduleList.add(schedule);
//            }
//        }
//        // 그 schedule 데이터들을 사용해 img값이 null인 medicationTake 객체들을 만들어 줌.
//        for(MedicationSchedule schedule: missingScheduleList){
//            MedicationTake medicationTake = MedicationTake.builder()
//                    .medicationSchedule(schedule)
//                    .timeOfTaking(LocalDateTime.now())
//                    .img(null)
//                    .build();
//            medicationTakeRepository.save(medicationTake);
//        }

        // 이미 존재하는 medicationTake 데이터의 scheduleId를 모으는 Set를 만듦.
        Set<Integer> existingScheduleIds = medicationTakeList.stream()
                .map(take -> take.getMedicationSchedule().getId())
                .collect(Collectors.toSet());
        // 누락된 스케줄을 찾아서 medicationTake에 추가함.
        for(MedicationSchedule schedule: medicationScheduleList){
            if(!existingScheduleIds.contains(schedule.getId())){
                MedicationTake medicationTake = MedicationTake.builder()
                    .medicationSchedule(schedule)
                    .timeOfTaking(LocalDateTime.now())
                    .img(null)
                    .medicationName(schedule.getMedication().getMedicationName())
                    .timeOfDay(schedule.getTimeOfDay())
                    .build();
            medicationTakeRepository.save(medicationTake);
            }
        }

    }


    @Transactional
    public List<MedicationRequestDto> requestHealthcareTodayPage() { // 건강관리 -당일 페이지
        List<MedicationRequestDto> medicationList = new ArrayList<>();

        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다.")); // 확인 필요

        List<MedicationTake> alreadyAddedList = new ArrayList<>();

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
                    String img = null;
                    LocalTime timeOfTaking = null;

                    for (MedicationTake medicationTake : medicationTakeRepository.findByMedicationSchedule(schedule)) {// 스케줄로 일단 다 찾은 다음 현재 날짜와 비교해서 있으면 true, 없으면 false
                        LocalDate storedDate = medicationTake.getTimeOfTaking().toLocalDate();
                        LocalDate currentDate = LocalDateTime.now().toLocalDate();

                        didTake = storedDate.isEqual(currentDate);
                        if(didTake){
                            img = medicationTake.getImg();
                            timeOfTaking = medicationTake.getTimeOfTaking().toLocalTime();
                            alreadyAddedList.add(medicationTake);
                        }
                    }
                    System.out.println("alreadyAddedList = " + alreadyAddedList);
                    // 이제 medicationList에 추가해주면 됨.
                    MedicationRequestDto medicationListDto = MedicationRequestDto.builder()
                                    .medicationId(medicationId)
                                    .medicationName(medicationName)
                                    .timeOfDay(timeOfDay)
                                    .didTake(didTake)
                                    .img(img)
                                    .timeOfTaking(timeOfTaking)
                                    .build();
                    medicationList.add(medicationListDto);

                }
            }

        }

        // 약이 삭제되기 전 이미 사용자가 약을 복용한 기록이 있을 때
        for(MedicationTake medicationTake: medicationTakeRepository.findByUser(user)){
            if(medicationTake.getTimeOfTaking().toLocalDate().isEqual(LocalDate.now())){
                if(!alreadyAddedList.contains(medicationTake)){
                    MedicationRequestDto medicationListDto = MedicationRequestDto.builder() // 이미 약을 복용했기 때문에 medicationId 필요없음. 찾을 수도 없고.
                            .medicationName(medicationTake.getMedicationName())
                            .timeOfDay(medicationTake.getTimeOfDay())
                            .didTake(true)
                            .img(medicationTake.getImg())
                            .timeOfTaking(medicationTake.getTimeOfTaking().toLocalTime())
                            .build();
                    medicationList.add(medicationListDto);
                }
            }
        }

        return medicationList;
    }

    @Transactional
    public MedicationCalendarDto requestHealthcareMonthPage(LocalDate date) { // 건강 관리 - month 페이지

        MedicationCalendarDto medicationCalendarDto = null;

        LocalDate today = LocalDate.now(); // 오늘 날짜

        User user = memberRepository.findById(JwtUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다.")); // 현재 사용자

        List<MedicationRequestDto> medicationRequestDtoList = new ArrayList<>();

        if (today.isAfter(date)) { // 오늘 날짜가 비교하는 날짜보다 이후인 경우
            // 과거에 복용했던 약 보여주기
//            for (Medication medication : medicationRepository.findByUser(user)) {
//                for (MedicationSchedule medicationSchedule : medicationScheduleRepository.findByMedication(medication)) {
//                    for (MedicationTake medicationTake : medicationTakeRepository.findByMedicationSchedule(medicationSchedule)) {
//                        if (medicationTake.getTimeOfTaking().toLocalDate().isEqual(date)) {
//                            MedicationRequestDto medicationRequestDto = MedicationRequestDto.builder()
//                                    .medicationId(medication.getId())
//                                    .medicationName(medication.getMedicationName())
//                                    .timeOfDay(medicationSchedule.getTimeOfDay())
//                                    .didTake(medicationTake.getImg() != null)
//                                    .img(medicationTake.getImg())
//                                    .timeOfTaking(medicationTake.getImg() == null ? null : medicationTake.getTimeOfTaking().toLocalTime())
//                                    .build();
//                            medicationRequestDtoList.add(medicationRequestDto);
//                        }
//                    }
//                }
//            }

            for (MedicationTake medicationTake : medicationTakeRepository.findByUser(user)) {
                if (medicationTake.getTimeOfTaking().toLocalDate().isEqual(date)) {
                    MedicationRequestDto medicationRequestDto = MedicationRequestDto.builder()  // 과거 약을 보여주는데 medicationId를 알려줄 필요가 없음.
                            .medicationName(medicationTake.getMedicationName())
                            .timeOfDay(medicationTake.getTimeOfDay())
                            .didTake(medicationTake.getImg() != null)
                            .img(medicationTake.getImg())
                            .timeOfTaking(medicationTake.getImg() == null ? null : medicationTake.getTimeOfTaking().toLocalTime())
                            .build();
                    medicationRequestDtoList.add(medicationRequestDto);
                }
            }

            medicationCalendarDto = MedicationCalendarDto.builder()
                    .date(java.sql.Date.valueOf(date))
                    .medicationRequestDtoList(medicationRequestDtoList)
                    .build();

        } else if (today.isEqual(date)) { // 오늘 날짜가 비교하는 날짜와 같은 경우
            // 오늘 날짜에 해당하는 약 리스트들 보여주기
            medicationRequestDtoList = requestHealthcareTodayPage();
            medicationCalendarDto = MedicationCalendarDto.builder()
                    .date(java.sql.Date.valueOf(date))
                    .medicationRequestDtoList(medicationRequestDtoList)
                    .build();

        } else {
            throw new IllegalStateException("날짜가 잘못되었습니다.");
        }

        return medicationCalendarDto;
    }

    @Transactional // 테스트 코드
    public void saveMedicationTakeTest(){
        MedicationTake medicationTake = new MedicationTake(5, medicationScheduleRepository.findById(1).get(), memberRepository.findById(1).get(), LocalDateTime.of(2023, 9, 6, 13, 24, 35), "https://laver-bucket.s3.ap-northeast-2.amazonaws.com/2022-03-20%20%2837%29.png", medicationScheduleRepository.findById(1).get().getMedication().getMedicationName(), medicationScheduleRepository.findById(1).get().getTimeOfDay());
        medicationTakeRepository.save(medicationTake);
        MedicationTake medicationTake12 = new MedicationTake(6, medicationScheduleRepository.findById(3).get(), memberRepository.findById(1).get(), LocalDateTime.of(2023, 9, 6, 8, 12, 59), "https://laver-bucket.s3.ap-northeast-2.amazonaws.com/2022-03-20%20%2837%29.png", medicationScheduleRepository.findById(3).get().getMedication().getMedicationName(), medicationScheduleRepository.findById(3).get().getTimeOfDay());
        medicationTakeRepository.save(medicationTake12);
        MedicationTake medicationTake2 = new MedicationTake(7, medicationScheduleRepository.findById(6).get(), memberRepository.findById(1).get(), LocalDateTime.of(2023, 9, 6, 20, 33, 24), "https://laver-bucket.s3.ap-northeast-2.amazonaws.com/2022-03-20%20%2837%29.png", medicationScheduleRepository.findById(6).get().getMedication().getMedicationName(), medicationScheduleRepository.findById(6).get().getTimeOfDay());
        medicationTakeRepository.save(medicationTake2);
    }

    // Medication을 삭제하고 외래 키 제약 조건을 관리하는 메서드
    @Transactional
    public void deleteMedicationWithConstraint(int medicationId){
        try{
            disableForeignKeyConstraint(); // 외래 키 제약 조건 해제
            medicationRepository.deleteById(medicationId); // Medication 삭제
        }finally {
            enableForeignKeyConstraint(); // 외래 키 제약 조건 설정
        }
    }

    // 외래 키 제약 조건 해제
    private void disableForeignKeyConstraint(){
        medicationRepository.disableForeignKeyConstraint();
    }

    // 외래 키 제약 조건 설정
    private void enableForeignKeyConstraint(){
        medicationRepository.enableForeignKeyConstraint();
    }

}