package com.works.documents;

import com.works.entities.Options;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Document(indexName = "survey")
@Data
public class ElasticSurvey {

    @Id
    private String seid = UUID.randomUUID().toString();


    @Field(type = FieldType.Integer)
    private Integer sid;

    @Field(type = FieldType.Text)
    private String stitle;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Options> options;
}
