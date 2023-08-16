package com.example.demo.repository;

import com.example.demo.domain.Medication;
import com.example.demo.domain.MedicationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Integer> {
    List<MedicationSchedule> findByMedication(Medication medication);

    Optional<MedicationSchedule> findByMedicationAndMondayAndTuesdayAndWednesdayAndThursdayAndFridayAndSaturdayAndSundayAndTimeOfDay(Medication needMedication, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday, LocalTime timeOfDay);
}
