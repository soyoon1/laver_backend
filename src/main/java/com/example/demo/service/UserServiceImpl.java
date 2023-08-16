package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.LoginRequestDto;
import com.example.demo.dto.MedicationInsertDTO;
import com.example.demo.dto.UserSignUpRequestDto;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService{

    @Value("${spring.security.secret}")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60 * 24L;   // 1000 * 60 * 60l 1시간  * 24 해서 하루, 24시간으로 바꿈.

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final MedicationService medicationService;


    @Transactional
    @Override
    public Integer signUp(UserSignUpRequestDto requestDto) throws Exception{
        if (memberRepository.findByLoginId(requestDto.getLoginId()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        if (!requestDto.getPassword().equals(requestDto.getCheckedPassword())){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        User user = memberRepository.save(requestDto.toEntity());
        user.encodePassword(passwordEncoder);

        user.addUserAuthority();

        // 이제 약을 저장할 차례
        // 새로운 MedicationInsertDTO를 만들어서 MedicationService에 있는 saveMedicationSchedule 매개변수로 집어 넣자.

        MedicationInsertDTO medicationInsertDTO = new MedicationInsertDTO();
        medicationInsertDTO.setUserId(String.valueOf(user.getId()));
        medicationInsertDTO.setMedicationName(requestDto.getMedicationName());
        medicationInsertDTO.setMonday(requestDto.isMonday());
        medicationInsertDTO.setTuesday(requestDto.isTuesday());
        medicationInsertDTO.setWednesday(requestDto.isWednesday());
        medicationInsertDTO.setThursday(requestDto.isThursday());
        medicationInsertDTO.setFriday(requestDto.isFriday());
        medicationInsertDTO.setSaturday(requestDto.isSaturday());
        medicationInsertDTO.setSunday(requestDto.isSunday());
        medicationInsertDTO.setTimeOfDay(requestDto.getTimeOfDay());

        medicationService.saveMedicationSchedule(medicationInsertDTO);

        return user.getId();
    }


    @Transactional
    @Override
    public User getLoginUserByLoginId(String loginId){
        if(loginId == null) return null;
        Optional<User> optionalUser = memberRepository.findByLoginId(loginId);
        if(optionalUser.isEmpty()) return null;
        return optionalUser.get();
    }

    @Override
    public User login(LoginRequestDto loginRequestDto) {
        Optional<User> optionalUser = memberRepository.findByLoginId(loginRequestDto.getLoginId());

        // loginId와 일치하는 User가 없으면 null return
        if(optionalUser.isEmpty()){
            return null;
        }

        User user = optionalUser.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            return null;
        }
//        if(!user.getPassword().equals(loginRequestDto.getPassword())){
//            return null;
//        }

        return user;

    }


}
