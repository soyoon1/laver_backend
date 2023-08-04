package com.example.demo.repository;

import com.example.demo.domain.Medication;
import com.example.demo.domain.MedicationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Integer> {
    Optional<MedicationSchedule> findByMedicationAndDayOfWeekAndTimeOfDay(Medication medication, String dayOfWeek, LocalTime timeOfDay);
    List<MedicationSchedule> findByMedication(Medication medication);
}
