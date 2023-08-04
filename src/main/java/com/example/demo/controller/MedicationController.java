package com.example.demo.controller;

import com.example.demo.domain.Medication;
import com.example.demo.domain.User;
import com.example.demo.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/medications")
@RestController
@RequiredArgsConstructor
public class MedicationController {
    private final MedicationService medicationService;

    @GetMapping("/new")
    public String createForm(Model model){
        model.addAttribute("medicationForm", new MedicationInsertDTO());
        return "medication/createMedicationForm";
    }

    @PostMapping("/new")
    public String create(@RequestBody @Valid MedicationInsertDTO medicationInsertDTO, BindingResult result){
        if(result.hasErrors()){
            return "members/createMedicationForm";
        }

        medicationService.saveMedicationSchedule(medicationInsertDTO);
        return "redirect:/medication";
    }

//    @GetMapping("/{medicationId}/edit")
//    public String updateMedicationForm(@PathVariable("medicationId") Integer medicationId, Model model){
//
//        Medication medication = medicationService.findOne(medicationId);
//
//        MedicationInsertDTO form = new MedicationInsertDTO();
//        form.setUserId();
//        form.setMedicationName();
//
//    }


}
