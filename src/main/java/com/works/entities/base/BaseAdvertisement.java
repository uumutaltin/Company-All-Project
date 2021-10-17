package com.works.entities.base;

import lombok.Data;
import org.springframework.data.annotation.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseAdvertisement {

    @Id
    private String raid = UUID.randomUUID().toString();

    private Integer aid;
    private String atitle;
    private Integer adesc;
    private Date datestart ;
    private Date dateend;
    private String advertimage;
    private String advertwidth;
    private String advertheight;
    private String advertlink;
    private Boolean astatus = true;

}
