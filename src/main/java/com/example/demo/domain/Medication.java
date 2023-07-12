package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Medication")
@Getter
@Setter
public class Medication {
    @Id
    @GeneratedValue
    @Column(name = "medication_id")
    private int id;

    // user_id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String medicationName;

}
