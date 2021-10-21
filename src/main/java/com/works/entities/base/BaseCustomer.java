package com.works.entities.base;

import lombok.Data;
import org.springframework.data.annotation.Id;


import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseCustomer {

    @Id
    private String rcid =  UUID.randomUUID().toString();


    private Integer cid;
    private String cname;
    private String csurname;
    private String email;
    private String mobile_phone;
    private Boolean ban=false;
}
