package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@ApiModel(value = "Reklam Model")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid", nullable = false)
    private Integer aid;


    @NotNull(message = "Reklam Adı boş olamaz")
    @NotEmpty(message = "Reklam Adı boş olamaz")
    private String atitle;

    @NotNull(message = "Gösterim Sayısı boş olamaz")
    private Integer adesc;

    @NotNull(message = "Başlangıç Tarihi boş olamaz")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date datestart ;

    @NotNull(message = "Bitiş Tarihi boş olamaz")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateend;



    private String advertimage;


    @NotNull(message = "Reklam Genişlik boş olamaz")
    @NotEmpty(message = "Reklam Genişlik boş olamaz")
    private String advertwidth;

    @NotNull(message = "Reklam Yükseklik boş olamaz")
    @NotEmpty(message = "Reklam Yükleklik boş olamaz")
    private String advertheight;

    @NotNull(message = "Reklam Link boş olamaz")
    @NotEmpty(message = "Reklam Link boş olamaz")
    private String advertlink;


    private Boolean astatus=true;












}
