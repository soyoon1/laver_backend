package com.example.demo.repository;

import com.example.demo.domain.Medication;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    List<Medication> findByMedicationName(String medicationName); // 속성을 정확히 쓰면 알아서 결과를 찾아준다.
    List<Medication> findByUser(User user);
    Medication findByUser_IdAndId(int userId, int medicationId);
}
