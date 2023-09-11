package com.example.demo.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class MedicationCalendarDto {
    Date date;
    List<MedicationRequestDto> medicationRequestDtoList;
}
