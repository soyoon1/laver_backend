package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "MedicationTake")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationTake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="medicationTake_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private MedicationSchedule medicationSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 약을 먹은 시간
    private LocalDateTime timeOfTaking;

    @Column(columnDefinition = "TEXT")
    private String img;

    @Column(length=30)
    private String medicationName;

    private LocalTime timeOfDay; // 복용해야 하는 시간

}