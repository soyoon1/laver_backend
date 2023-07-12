package com.example.demo.repository;

import com.example.demo.domain.Checklist;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
}
