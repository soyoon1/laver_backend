package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ChecklistItem")
@Getter
@Setter
public class ChecklistItem {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private int id;

    // checklist_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    // user_id 생략
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

    private Boolean isBool;
    private Boolean boolAnswer;
    @Column(length = 100)
    private String textQuestion;
    @Column(length = 100)
    private String textAnswer;

}
