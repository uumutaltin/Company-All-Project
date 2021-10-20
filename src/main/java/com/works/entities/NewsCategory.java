package com.works.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class NewsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ncid", nullable = false)
    private Integer ncid;


    @NotNull(message = "Kategori Adı boş olamaz")
    @NotEmpty(message = "Kategori Adı boş olamaz")
    private String nctitle;
}
