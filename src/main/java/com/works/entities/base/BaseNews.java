package com.works.entities.base;

import com.works.entities.NewsCategory;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseNews {

    @Id
    private String rnid = UUID.randomUUID().toString();

    private Integer nid;
    private String ntitle;
    private String ndescription;
    private String ndetail;
    private String newsimage;
    private Boolean nstatus=true;
    private NewsCategory newsCategory;
    private Date ndate;


}
