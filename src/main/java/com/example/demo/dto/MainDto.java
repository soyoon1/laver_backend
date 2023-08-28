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
public class MainDto {
    private Date createdDate; // 복용 일수를 계산하기 위함.

    private List<MedicationListDto> medicationList;
//    private List<MedicationListDto> afternoonMedicationList;
}
