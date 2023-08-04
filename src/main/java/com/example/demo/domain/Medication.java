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

//    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL) // 양방향 매핑 읽기 전용
//    private List<MedicationSchedule> medicationSchedules = new ArrayList<>();


    // 생성 메서드
    public static Medication createMedication(User user, String medicationName){
        Medication medication = new Medication();
        medication.setUser(user);
        medication.setMedicationName(medicationName);
        return medication;
    }

    //    // 연관관계 메서드 양방향 매핑
//    public void setUser(User user){
//        this.user = user;
//        // 약 이름이 다른 경우에만 user에 약을 추가한다.
//        boolean result = isItSame(user, this.medicationName); // 같은 값이 있는지 확인
//
//        if(!result)
//            user.getMedications().add(this);
//    }
//
//    public void addMedicationSchedule(MedicationSchedule medicationSchedule){
//        medicationSchedules.add(medicationSchedule);
//        medicationSchedule.setMedication(this);
//    }
//    // 생성 메서드
//    public static Medication createMedication(User user, String medicationName, MedicationSchedule... medicationSchedules){
//        Medication medication = new Medication();
//        medication.setUser(user);
//        medication.setMedicationName(medicationName);
//        for (MedicationSchedule medicationSchedule : medicationSchedules){
//            medication.addMedicationSchedule(medicationSchedule);
//        }
//        return medication;
//    }
//
//
//    public boolean isItSame(User user, String medicationName){
//        boolean result = false; // 같은 값이 있는지 확인
//        for(Medication medication : user.getMedications()){
//            if(medication.getMedicationName().equals(this.medicationName)) {
//                result = true;
//                break;
//            }
//        }
//        return result;
//    }


}
