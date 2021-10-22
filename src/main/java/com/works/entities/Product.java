package com.works.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid", nullable = false)
    private Integer pid;

    @NotNull(message = "Ürün Adı boş olamaz")
    @NotEmpty(message = "Ürün Adı boş olamaz")
    private  String pname;

    @NotNull(message = "Ürün Kısa Detay boş olamaz")
    @NotEmpty(message = "Ürün Kısa Detay boş olamaz")
    private  String psdetail;

    @NotNull(message = "Ürün Detay boş olamaz")
    @NotEmpty(message = "Ürün Detay boş olamaz")
    private  String pdetail;

    @NotNull(message = "Ürün Fiyat boş olamaz")
    private Integer pprice;

    @NotNull(message = "Ürün tipi boş olamaz")
    private Integer ptype;

    @NotNull(message = "Kampanya  boş olamaz")
    private Integer pcampaign;

    @NotNull(message = "Kampanya Adı boş olamaz")
    private String pcampaignname;

    @NotNull(message = "Kampanya Detay boş olamaz")
    private String pcampaigndetail;

    @NotNull(message = "Adres boş olamaz")
    @NotEmpty(message = "Adres  boş olamaz")
    private String padress;

    @NotNull(message = "Enlem boş olamaz")
    @NotEmpty(message = "Enlem boş olamaz")
    private String platitude ;

    @NotNull(message = "Boylam boş olamaz")
    @NotEmpty(message = "Boylam boş olamaz")
    private String plongitude;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ProductImages> pimages;

    @ManyToMany(cascade = CascadeType.DETACH)
    private List<ProductCategory> pcategories;

}
