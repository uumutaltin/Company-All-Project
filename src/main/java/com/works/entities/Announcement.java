package com.works.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid", nullable = false)
    private Integer aid;

    @NotNull(message = "Duyuru Başlık boş olamaz")
    @NotEmpty(message = "Duyuru Başlık boş olamaz")
    private String atitle;

    @NotNull(message = "Detay boş olamaz")
    @NotEmpty(message = "Detay boş olamaz")
    private String adetail;

    private Boolean astatus = true;

    private Date adate = new Date();


}
