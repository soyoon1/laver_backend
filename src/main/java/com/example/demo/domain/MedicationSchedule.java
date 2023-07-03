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
    @GeneratedValue
    @Column(name = "schedule_id")
    private int scheduleId;

    // medication_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id")
    private Medication medication;

    // user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private int dosage;
    private String day_of_week;
    private LocalTime time_of_day;  // 나중에 포맷을 정해줘야 함.
}
