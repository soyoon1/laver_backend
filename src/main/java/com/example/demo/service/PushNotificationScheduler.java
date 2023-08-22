package com.example.demo.service;

import com.example.demo.domain.Medication;
import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.User;
import com.example.demo.repository.MedicationRepository;
import com.example.demo.repository.MedicationScheduleRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

        for (User user : allUsers) {
            System.out.println(user.getId());
            if (!user.isAlarm()) {
                System.out.println("user가 알림 설정을 하지 않았습니다.");
                break; // 알림 설정을 안 했을 시에 반복문을 빠져나감.
            }
            List<Medication> medications = medicationRepository.findByUser(user);  // 특정 사용자가 등록한 약 이름 리스트

            List<MedicationSchedule> totalSchedules = new ArrayList<>();
            List<MedicationSchedule> eachSchedules;
            for (Medication medication : medications) {  // 약 이름마다 있는 약 스케줄 리스트
                eachSchedules = medicationScheduleRepository.findByMedication(medication);
                // 사용자의 약 이름마다 있는 약 스케줄을 한 곳으로 모음.
                totalSchedules.addAll(eachSchedules);
            }

            // 요일이 boolean 타입을 가진 속성 7개에 각각 들어간다고 할 때

            for (MedicationSchedule schedule : totalSchedules) {                 // 스케줄 데이터의 요일, 시간을 현재 요일, 시간과 비교해 같으면 알림 발송
                boolean isScheduleDay = false;
                switch (formattedDay) {    // 오늘 요일이 무엇인지에 따라 오늘 요일이 약을 먹어야 하는 요일인지 알 수 있음.
                    case "월":
                        isScheduleDay = schedule.isMonday();
                        break;
                        ///
                    case "일":
                        isScheduleDay = schedule.isSunday();
                        break;
                }

                if (isScheduleDay) {    // 약 스케줄에 해당 요일 boolean 타입이 true일 때(해당 요일에 이 약을 먹어야 할 때)
                    if (String.valueOf(schedule.getTimeOfDay()).equals(formattedTime)) { // 해당 시간에 약을 먹어야 할 때
                        // 이미지가 추가되어 있지 않으면(약 복용을 인증하지 않았을 경우)
                        if (schedule.getImg() == null) {
                            System.out.println("약 스케줄에 따른 알림 발송" + schedule.getTimeOfDay() + formattedTime);  // 알림이 제대로 가는지 확인할 수 있음.
                            String targetToken = user.getFcmToken();
                            String title = "약을 복용할 시간입니다!";
                            String body = "약을 복용하고 인증해주세요.";
                            String sound = "default";

                            firebaseCloudMessageService.sendMessageTo(targetToken, title, body, sound);
                        }
                    }
                }
            }
        }
    }
        //                    System.out.println(schedule.getDayOfWeek()+formattedDay);
//                    System.out.println(schedule.getTimeOfDay());
//                    System.out.println(formattedTime);


        // 요일이 그냥 "금" 이렇게 저장된다고 생각할 때
//
//            for (MedicationSchedule schedule : totalSchedules){                 // 스케줄 데이터의 요일, 시간을 현재 요일, 시간과 비교해 같으면 알림 발송
//                if(schedule.getDayOfWeek().equals(formattedDay)){    // 이거 CharAt 으로 바꿔야 함.
//                    if(String.valueOf(schedule.getTimeOfDay()).equals(formattedTime)){
//                        // 이미지가 추가되어 있지 않으면(약 복용을 인증하지 않았을 경우)
//                        if(schedule.getImg() == null) {
//                            System.out.println("약 스케줄에 따른 알림 발송" + schedule.getTimeOfDay() + formattedTime);  // 알림이 제대로 가는지 확인할 수 있음.
//                            String targetToken = user.getFcmToken();
//                            String title = "약을 복용할 시간입니다!";
//                            String body = "약을 복용하고 인증해주세요.";
//                            String sound = "default";
//
//                            firebaseCloudMessageService.sendMessageTo(targetToken, title, body, sound);
//                        }
//                    }
//                }
//            }

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


        // 테스트 코드
//        System.out.println();
//        System.out.println("그냥 1분마다 발송되는 알림");
//        String targetToken = "coVTsxsWQtehbsmmREVMFF:APA91bHxD5K67NGdhAJWB3ZtL9WjULa5nKgKLXvGtoA0jL3VlI1xxqoJjmQJVPSQ73KpuMZQ47wBK-pCZ32jOa3-iJepXvd6ViPeKYIn0E-5LvfN3XTzS9MJBhBkmiB-e5qf5TiQfCPn"; // 디바이스 토큰은 푸시 알림을 전송할 디바이스를 식별하는 값  나중에 데이터베이스에서 가져오도록 하겠다.
//        String title = "1분마다 알림이 울릴까요?";
//        String body = "약을 복용하고 인증해주세요.";
//        String sound = "default";
//        firebaseCloudMessageService.sendMessageTo(targetToken, title, body, sound);

   // }
















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
