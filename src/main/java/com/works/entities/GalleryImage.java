package com.works.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class GalleryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "giid", nullable = false)
    private Integer giid;

    private String giname;
    private String gisubtitle;


}
