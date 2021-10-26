package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@ApiModel(value = "İçerik Model")
public class Contents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid", nullable = false)
    private Integer cid;


    @NotNull(message = "İçerik Başlık boş olamaz")
    @NotEmpty(message = "İçerik Başlık boş olamaz")
    private String ctitle;

    @NotNull(message = "Kısa Açıklama boş olamaz")
    @NotEmpty(message = "Kısa Açıklama boş olamaz")
    private String cdescription;

    @NotNull(message = "Detaylı Açıklama boş olamaz")
    @NotEmpty(message = "Detaylı Açıklama boş olamaz")
    private String cdetail;

    private Boolean cstatus = true;

    private Date cdate = new Date();


}
