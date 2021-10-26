package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@ApiModel(value = "Galeri Model")
public class Gallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid", nullable = false)
    private Integer gid;

    @NotNull(message = "Galeri Adı boş olamaz")
    @NotEmpty(message = "Galeri Adı boş olamaz")
    private String gname;


    private String gdetail;
    private Boolean gstatus;

    @OneToMany(cascade = CascadeType.REMOVE)
    List<GalleryImage> galleryImages;


}
