package com.works.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class ProductCategories {
    @Id
    @Column(name = "pcsid", nullable = false)
    private Integer pcsid;

    @NotNull(message = "Kategori Adı null olamaz")
    @NotEmpty(message = "Kategori Adı boş olamaz")
    private String pcategoriesname;

    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<ProductCategory> pcategory;
}
