package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Medication")
@Getter
@Setter
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id")
    private int id;

    // user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length=30)
    private String medicationName;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL) // 양방향 매핑 읽기 전용
    private List<MedicationSchedule> medicationSchedules = new ArrayList<>();


    // 생성 메서드
    public static Medication createMedication(User user, String medicationName){
        Medication medication = new Medication();
        medication.setUser(user);
        medication.setMedicationName(medicationName);
        return medication;
    }

}
