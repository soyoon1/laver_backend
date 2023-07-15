package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

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

    // user_id 생략
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    private int dosage;
    @Column(length=5)
    private String dayOfWeek;
    private LocalTime timeOfDay;  // 나중에 포맷을 정해줘야 함.

    // 약을 먹은 시간
    private LocalTime timeOfTaking; // 값이 있는지 없는지로 약 복용 판단. String으로 취급해도 괜찮을까

}
