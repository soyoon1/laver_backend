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

    private boolean monday;  // 해당 요일에 약을 먹어야 하는지 알려줌.
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private boolean sunday;
    private LocalTime timeOfDay;  // 나중에 포맷을 정해줘야 함.

    @OneToMany(mappedBy = "medicationSchedule", cascade = CascadeType.PERSIST) // 양방향 매핑 읽기 전용
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

}
