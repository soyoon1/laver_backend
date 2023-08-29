package com.example.demo.repository;

import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.MedicationTake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationTakeRepository extends JpaRepository<MedicationTake, Integer> {

    List<MedicationTake> findByMedicationSchedule(MedicationSchedule medicationSchedule);
}
