package com.works.entities;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
@ApiModel(value = "Kullanıcı Model")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer uid;

    @NotNull(message = "Şirket Adı boş olamaz")
    @NotEmpty(message = "Şirket Adı boş olamaz")
    private String companyname;

    @NotNull(message = "Şirket Adresi boş olamaz")
    @NotEmpty(message = "Şirket Adresi boş olamaz")
    private String adress;

    @NotNull(message = "Sektör boş olamaz")
    private int sector;

    @NotNull(message = "GSM boş olamaz")
    @NotEmpty(message = "GSM boş olamaz")
    private String phone;

    @NotNull(message = "Yetkili Adı boş olamaz")
    @NotEmpty(message = "Yetkili Adı boş olamaz")
    private String username;

    @NotNull(message = "Yetkili Soyadı boş olamaz")
    @NotEmpty(message = "Yetkili Soyadı boş olamaz")
    private String surname;

    @NotNull(message = "Yetkili Mail boş olamaz")
    @NotEmpty(message = "Yetkili Mail boş olamaz")
    private String mail;

    @NotNull(message = "Şifre boş olamaz")
    @NotEmpty(message = "Şifre Adı boş olamaz")
    private String password;

    private boolean enabled;
    private boolean tokenExpired;


    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "rid"))
    private List<Role> roles;

}
