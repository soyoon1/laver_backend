//package com.example.demo.domain;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalTime;
//import java.util.Date;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "MedicationTake")
//@NoArgsConstructor
//@AllArgsConstructor
//public class MedicationTake {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="user_id")
//    private int id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "schedule_id")
//    private MedicationSchedule medicationSchedule;
//
//    // 약을 먹은 시간
//    private Date timeOfTaking;
//
//    private String img;
//
//}
