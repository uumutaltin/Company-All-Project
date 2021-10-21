package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@ApiModel(value = "Müşteri Model",description = "Yeni Müşteri Ekleme için Kullanılır.")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid", nullable = false)
    private Integer cid;

    @NotNull(message = "Customer cname NotNull")
    @NotEmpty(message = "Customer cname NotEmpty")
    private String cname;

    @NotNull(message = "Customer csurname NotNull")
    @NotEmpty(message = "Customer csurname NotEmpty")
    private String csurname;

    @NotNull(message = "Customer email NotNull")
    @NotEmpty(message = "Customer email NotEmpty")
    @Column(unique = true)
    private String  email;

    @NotNull(message = "Customer mobile_phone NotNull")
    @NotEmpty(message = "Customer mobile_phone NotEmpty")
    private String mobile_phone;

    private Boolean ban=false;

}
