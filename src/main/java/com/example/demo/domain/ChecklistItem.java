package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ChecklistItem")
@Getter
@Setter
public class ChecklistItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int id;

    // checklist_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    // user_id 생략
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isBool;
    private Boolean boolAnswer;
    @Column(length = 100)
    private String textQuestion;
    @Column(length = 100)
    private String textAnswer;

    public ChecklistItem(Checklist checklist, Boolean isBool, Boolean boolAnswer, String textQuestion, String textAnswer){
        this.checklist=checklist;
        this.isBool =isBool;
        this.boolAnswer = boolAnswer;
        this.textQuestion = textQuestion;
        this.textAnswer = textAnswer;
    }


}
