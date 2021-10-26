package com.works.entities.restentity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class SurveyOptionRest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "soid", nullable = false)
    private Integer soid;

    private Integer socid;
    private Integer sooid;
    private Integer sosid;

}
