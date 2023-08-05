package com.example.demo.service;

import com.example.demo.domain.Medication;
import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.User;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.repository.MedicationScheduleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class PushNotificationScheduler {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final MemberRepository memberRepository;
    private final MedicationRepository medicationRepository;
    private final MedicationScheduleRepository medicationScheduleRepository;

    public PushNotificationScheduler(FirebaseCloudMessageService firebaseCloudMessageService,
                                     MemberRepository memberRepository,
                                     MedicationRepository medicationRepository,
                                     MedicationScheduleRepository medicationScheduleRepository) {
        this.firebaseCloudMessageService = firebaseCloudMessageService;
        this.memberRepository = memberRepository;
        this.medicationRepository = medicationRepository;
        this.medicationScheduleRepository = medicationScheduleRepository;
    }

        // 특정 시간에 스케줄링하여 실행되도록 설정
    @Scheduled(cron = "0 * * * * ?") // 매 분마다 실행
    public void checkAndSendPushNotification() throws IOException {

        // 현재 요일을 가져옴. 원하는 형식으로 요일을 포맷팅
        LocalDateTime now = LocalDateTime.now();
        String formattedDay = now.format(DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko")));
        System.out.println("current day: " + formattedDay);

        // 현재 시간을 가져옴. 원하는 형식으로 시간을 포맷팅
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = currentTime.format(formatter);

        System.out.println("current time: " + formattedTime);

        // 데이터베이스에서 모든 사용자 정보를 조회
        List<User> allUsers = memberRepository.findAll();

        for(User user : allUsers){
            System.out.println(user.getId());
            List<Medication> medications = medicationRepository.findByUser(user);  // 특정 사용자가 등록한 약 이름 리스트

            List<MedicationSchedule> totalSchedules = new ArrayList<>();
            List<MedicationSchedule> eachSchedules;
            for(Medication medication : medications){  // 약 이름마다 있는 약 스케줄 리스트
                eachSchedules = medicationScheduleRepository.findByMedication(medication);
                // 사용자의 약 이름마다 있는 약 스케줄을 한 곳으로 모음.
                totalSchedules.addAll(eachSchedules);
            }


            // 요일이 "금, 토" 이렇게 저장될수도 있다고 생각할 때
    //            for (MedicationSchedule schedule : schedules){                 // 스케줄 데이터의 요일, 시간을 현재 요일, 시간과 비교해 같으면 알림 발송
    //                String days = schedule.getDayOfWeek(); // 스트링 문자
    //                char[] eachDay = new char[days.length()]; // 스트링을 담을 배열
    //
    //                for(int i=0; i<eachDay.length; i++){
    //                    eachDay[i] = (days.charAt(i)); // 스트링을 한 글자씩 끊어 배열에 저장
    //                }
    //
    //                for(char charDayOfWeek : eachDay){    // char타입의 한글자씩 끊어낸 결과물들 ex) [0]금 [1], [2]  [3]토
    //                    String StringDayOfWeek = String.valueOf(charDayOfWeek); // String 타입으로 형변환
    //                    if(StringDayOfWeek.equals(formattedDay)){
    //                        if(schedule.getTimeOfDay().equals(formattedTime)){
    //                            String targetToken = user.getFcmToken();
    //                            String title = "약을 복용할 시간입니다!";
    //                            String body = "약을 복용하고 인증해주세요.";
    //
    //                            firebaseCloudMessageService.sendMessageTo(targetToken, title, body);
    //                        }
    //                    }
    //
    //                }
    //
    //            }

            // 요일이 그냥 "금" 이렇게 저장된다고 생각할 때

            for (MedicationSchedule schedule : totalSchedules){                 // 스케줄 데이터의 요일, 시간을 현재 요일, 시간과 비교해 같으면 알림 발송
                if(schedule.getDayOfWeek().equals(formattedDay)){    // 이거 CharAt 으로 바꿔야 함.
//                    System.out.println(schedule.getDayOfWeek()+formattedDay);
//                    System.out.println(schedule.getTimeOfDay());
//                    System.out.println(formattedTime);
                    if(String.valueOf(schedule.getTimeOfDay()).equals(formattedTime)){
                        System.out.println("약 스케줄에 따른 알림 발송" + schedule.getTimeOfDay()+formattedTime);  // 알림이 제대로 가는지 확인할 수 있음.
                        String targetToken = user.getFcmToken();
                        String title = "약을 복용할 시간입니다!";
                        String body = "약을 복용하고 인증해주세요.";
                        String sound = "default";

                        firebaseCloudMessageService.sendMessageTo(targetToken, title, body, sound);
                    }
                }
            }


        }

        // 테스트 코드
        System.out.println();
        System.out.println("그냥 1분마다 발송되는 알림");
        String targetToken = "eV0BlQKaThKxlAk28Y4yHb:APA91bEC0ECiuHHeWAE8ZfNhA9sRvr6TYJ9lXmLsn_4rwebeNknBCLLHRyyE1vHq7JKi5oeoem8UEp4pFQG_4omu2f7XNczVZ-_PHZW2K3ZQ3hLpsgAKgigxqYg7TxzEwnOdxYNSf4uD"; // 디바이스 토큰은 푸시 알림을 전송할 디바이스를 식별하는 값  나중에 데이터베이스에서 가져오도록 하겠다.
        String title = "1분마다 알림이 울릴까요?";
        String body = "약을 복용하고 인증해주세요.";
        String sound = "default";
        firebaseCloudMessageService.sendMessageTo(targetToken, title, body, sound);

    }
















//    private final FirebaseCloudMessageService firebaseCloudMessageService;
//    private MedicationRepository medicationRepository;
//    private MedicationScheduleRepository medicationScheduleRepository;
//    private MemberRepository memberRepository;

//    public PushNotificationScheduler(FirebaseCloudMessageService firebaseCloudMessageService) {
//        this.firebaseCloudMessageService = firebaseCloudMessageService;
//    }
//
//    // 특정 시간에 스케줄링하여 실행되도록 설정  어떻게 User 정보를 들고 오지?
//    @Scheduled(cron = "0 * * * * ?") // 매 분마다 실행
//    public void checkAndSendPushNotification() throws IOException {
//        String databaseTime = "00:40"; // 데이터베이스에 저장된 시간 이것도 시간을 포맷팅해야함
//
//        String databaseDay = "금"; // 데이터베이스에 저장된 요일로 수정해야 함.
//
//        // 현재 요일을 가져옴. 원하는 형식으로 요일을 포맷팅
//        LocalDateTime now = LocalDateTime.now();
//        String formattedDay = now.format(DateTimeFormatter.ofPattern("E").withLocale(Locale.forLanguageTag("ko")));
//        System.out.println("current day: " + formattedDay);
//
//        // 현재 시간을 가져옴. 원하는 형식으로 시간을 포맷팅
//        LocalTime currentTime = LocalTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
//        String formattedTime = currentTime.format(formatter);
//
//        System.out.println("current time: " + formattedTime);
//
//
//
//        if (databaseTime.equals(formattedTime)) {
//            if(databaseDay.equals(formattedDay)){ // 이것도 수정해야 함.
//                // 조건이 충족되면 푸시 알림 발송
//                String targetToken = "fHQgBwOkSLuaXpHfoKKIFd:APA91bGkHjhPf2fdAaqjdnTL_5Bui_q-GX4dUyDfjxmgUt4_Un5orbFeSPA7I4KbUvfX77646Nv0AXTcl3fb8eHwDFnRY9KM0COo-Vcf5vJJNM-TrjcrO0uQe09W-wyRVaZIjltSuvL0"; // 디바이스 토큰은 푸시 알림을 전송할 디바이스를 식별하는 값  나중에 데이터베이스에서 가져오도록 하겠다.
//                String title = "약을 복용할 시간입니다!";
//                String body = "약을 복용하고 인증해주세요.";
//
//                firebaseCloudMessageService.sendMessageTo(targetToken, title, body);
//            }
//        }
//
//        String targetToken = "fHQgBwOkSLuaXpHfoKKIFd:APA91bGkHjhPf2fdAaqjdnTL_5Bui_q-GX4dUyDfjxmgUt4_Un5orbFeSPA7I4KbUvfX77646Nv0AXTcl3fb8eHwDFnRY9KM0COo-Vcf5vJJNM-TrjcrO0uQe09W-wyRVaZIjltSuvL0"; // 디바이스 토큰은 푸시 알림을 전송할 디바이스를 식별하는 값  나중에 데이터베이스에서 가져오도록 하겠다.
//        String title = "1분마다 알림이 울릴까요?";
//        String body = "약을 복용하고 인증해주세요.";
//        firebaseCloudMessageService.sendMessageTo(targetToken, title, body);
//    }
}
