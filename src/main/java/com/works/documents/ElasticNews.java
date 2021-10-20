package com.works.documents;

import com.works.entities.NewsCategory;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.UUID;

@Document(indexName = "news")
@Data
public class ElasticNews {

    @Id
    private String neid = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer)
    private Integer nid;

    @Field(type = FieldType.Text)
    private String ntitle;

    @Field(type = FieldType.Text)
    private String ndescription;

    @Field(type = FieldType.Text)
    private String ndetail;

    @Field(type = FieldType.Text)
    private String newsimage;

    @Field(type = FieldType.Boolean)
    private Boolean nstatus;

    @Field(type = FieldType.Nested, includeInParent = true)
    private NewsCategory newsCategory;

    @Field(type = FieldType.Date_Range, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date ndate;

}
