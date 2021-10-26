package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@ApiModel(value = "Anket Seçenek Model")
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid", nullable = false)
    private Integer oid;

    @NotNull(message = "Seçenek Alanı boş olamaz")
    @NotEmpty(message = "Seçenek Alanı boş olamaz")
    private String soption;

    @Column(columnDefinition = "integer default 0")
    private  int voteNumber;


}
