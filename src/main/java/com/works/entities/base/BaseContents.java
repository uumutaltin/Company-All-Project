package com.works.entities.base;

import lombok.Data;
import org.springframework.data.annotation.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseContents {

    @Id
    private String rcid = UUID.randomUUID().toString();


    private Integer cid;
    private String ctitle;
    private String cdescription;
    private String cdetail;
    private Boolean cstatus = true;
    private Date cdate;




}
