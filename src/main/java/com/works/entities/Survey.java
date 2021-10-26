package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@ApiModel(value = "Anket Model")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", nullable = false)
    private Integer sid;

    @NotNull(message = "Anket Başlık Alanı Boş Olamaz")
    @NotEmpty(message = "Anket Başlık Alanı Boş Olamaz")
    private String stitle;

    @OneToMany
    private List<Options> options;






}
