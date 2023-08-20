package com.example.demo.controller;

import com.example.demo.dto.RequestDTO;
import com.example.demo.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/alarm")
@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException{
        System.out.println(requestDTO.getTargetToken() + " " + requestDTO.getTitle() + " " + requestDTO.getBody() + " " + requestDTO.getSound());

        firebaseCloudMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody(),
                requestDTO.getSound());
        return ResponseEntity.ok().build();
    }

}
