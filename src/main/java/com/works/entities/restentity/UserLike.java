package com.works.entities.restentity;


import com.works.entities.Customer;
import com.works.entities.Product;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class UserLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lid", nullable = false)
    private Integer lid;

    private Integer customerid;


    private Integer productid;

    private Integer userpoint;

    private Date likedate = new Date();

}
