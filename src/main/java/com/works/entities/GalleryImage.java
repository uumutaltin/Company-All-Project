package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@ApiModel(value = "Galeri Fotoğraf Model")
public class GalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "giid", nullable = false)
    private Integer giid;

    private String giname;
    private String gisubtitle;


}
