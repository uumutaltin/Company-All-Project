package com.works.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ApiModel(value = "Rol Model")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<Users> users;
}
