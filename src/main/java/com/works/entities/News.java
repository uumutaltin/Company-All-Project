package com.works.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid", nullable = false)
    private Integer nid;

    @NotNull(message = "Haber Başlık boş olamaz")
    @NotEmpty(message = "Haber Başlık boş olamaz")
    private String ntitle;

    @NotNull(message = "Kısa Açıklama boş olamaz")
    @NotEmpty(message = "Kısa Açıklama boş olamaz")
    private String ndescription;

    @NotNull(message = "Uzun Açıklama boş olamaz")
    @NotEmpty(message = "Uzun Açıklama boş olamaz")
    private String ndetail;

    private String newsimage;
    private Boolean nstatus=true;
    private Date ndate = new Date();

    @OneToOne
    private NewsCategory newsCategory;

}
