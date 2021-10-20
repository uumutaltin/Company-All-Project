package com.works.entities.base;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseAnnouncement {

    @Id
    private String raid = UUID.randomUUID().toString();

    private Integer aid;
    private String atitle;
    private String adetail;
    private Boolean astatus = true;
    private Date adate;




}
