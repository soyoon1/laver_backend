package com.example.demo.controller;

import com.example.demo.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter @Setter
public class MedicationInsertDTO {

    private String userId;
    private String medicationName;
    private String dayOfWeek;
    private LocalTime timeOfDay;

}
