package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MedicationSchedule")
@Getter
@Setter
public class MedicationSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int id;

    // medication_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id")
    private Medication medication;

//    // user_id 생략  없애야 함.
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

//    private int dosage; 없애야 함.
//    @Column(length=5)
//    private String dayOfWeek; 없애야 함.

    private boolean monday;  // 해당 요일에 약을 먹어야 하는지 알려줌.
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private LocalTime timeOfDay;  // 나중에 포맷을 정해줘야 함.

    // 약을 먹은 시간
//    private LocalTime timeOfTaking; // 값이 있는지 없는지로 약 복용 판단. String으로 취급해도 괜찮을까 테이블을 새로 만듦.

//    private String img; 테이블을 새로 만듦.

    @OneToMany(mappedBy = "medicationSchedule", cascade = CascadeType.ALL) // 양방향 매핑 읽기 전용
    private List<MedicationTake> medicationTakeList = new ArrayList<>();

    // 생성메서드
    public static MedicationSchedule createMedicationSchedule(Medication medication, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, LocalTime timeOfDay){
        MedicationSchedule medicationSchedule = new MedicationSchedule();
        medicationSchedule.setMedication(medication);
        medicationSchedule.setMonday(monday);
        medicationSchedule.setTuesday(tuesday);
        medicationSchedule.setWednesday(wednesday);
        medicationSchedule.setThursday(thursday);
        medicationSchedule.setFriday(friday);
        medicationSchedule.setSaturday(saturday);
        medicationSchedule.setSunday(sunday);
        medicationSchedule.setTimeOfDay(timeOfDay);
        return medicationSchedule;
    }

//    public static MedicationSchedule createMedicationSchedule(String dayOfWeek, LocalTime timeOfDay){
//        MedicationSchedule medicationSchedule = new MedicationSchedule();
//        medicationSchedule.setDayOfWeek(dayOfWeek);
//        medicationSchedule.setTimeOfDay(timeOfDay);
//        return medicationSchedule;
//    }

}
