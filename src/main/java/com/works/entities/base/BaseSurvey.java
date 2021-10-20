package com.works.entities.base;

import com.works.entities.Options;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@MappedSuperclass
public class BaseSurvey {

    @Id
    private String said = UUID.randomUUID().toString();

    private Integer sid;

    private String stitle;
    private List<Options> options = new ArrayList<>();



}
