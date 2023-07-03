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
    private int itemId;

    // checklist_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checklist_id")
    private Checklist checklist;

    // user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean is_bool;
    private Boolean bool_answer;
    private String text_question;
    private String text_answer;

}
