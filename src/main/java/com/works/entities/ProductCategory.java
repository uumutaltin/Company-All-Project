package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@ApiModel(value = "Ürün Kategori Model")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pcid", nullable = false)
    private Integer pcid;

    @NotNull(message = "Kategori Adı null olamaz")
    @NotEmpty(message = "Kategori Adı boş olamaz")
    private String pcategoryname;



}
