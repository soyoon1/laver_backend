package com.example.demo.repository;

import com.example.demo.domain.MedicationSchedule;
import com.example.demo.domain.MedicationTake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MedicationTakeRepository extends JpaRepository<MedicationTake, Integer> {

    List<MedicationTake> findByMedicationSchedule(MedicationSchedule medicationSchedule);
    List<MedicationTake> findByTimeOfTaking(LocalDate today);

    List<MedicationTake> findByTimeOfTakingBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
