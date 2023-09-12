package com.example.demo.domain;

import com.example.demo.dto.ChecklistRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "checklist")
@Getter
@Setter
@NoArgsConstructor
public class Checklist extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checklist_id") // 체크리스트 아이디
    private int id;

    // user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private boolean q1;

    @Column(nullable = false)
    private boolean q2;
    @Column(nullable = false)
    private String q3;

    private LocalDate localDate;


    public Checklist(User user, ChecklistRequestDto requestDto){
        this.q1=requestDto.isQ1();
        this.q2=requestDto.isQ2();
        this.q3=requestDto.getQ3();
        this.localDate=requestDto.getLocalDate();
        this.user=user;
    }

}
