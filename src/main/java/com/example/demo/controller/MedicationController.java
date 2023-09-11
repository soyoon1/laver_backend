package com.example.demo.controller;

import com.example.demo.dto.MedicationInsertDTO;
import com.example.demo.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/medications")
@RestController
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("medicationForm", new MedicationInsertDTO());
        return "redirect:/medication/createMedicationForm";
    }

    // /user/medication-signup
    @PostMapping("/new")
    public String create(@RequestBody @Valid MedicationInsertDTO medicationInsertDTO, BindingResult result){
        if(result.hasErrors()){
            return "members/createMedicationForm";
        }

        medicationService.saveMedicationSchedule(medicationInsertDTO);
        return "redirect:/medication";
    }

    @GetMapping("/medicationTake") // medicationTake 데이터 임의로 삽입
    public String createMedicationTake(){
        medicationService.saveMedicationTakeTest();
        return "medicationTake 저장 완료";
    }

}
