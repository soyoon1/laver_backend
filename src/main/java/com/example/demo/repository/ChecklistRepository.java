package com.example.demo.repository;

import com.example.demo.domain.Checklist;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ChecklistRepository extends JpaRepository<Checklist, Integer> {
    Optional<Checklist> findByLocalDate(LocalDate date);

}
