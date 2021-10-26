package com.works.entities.restentity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid", nullable = false)
    private Integer oid;

    private Integer customerid;

    private Integer productid;

    private Boolean orderstatus = false;

    private Date orderDate = new Date();

    private String adress;

}
