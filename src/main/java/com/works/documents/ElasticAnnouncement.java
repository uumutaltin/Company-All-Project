package com.works.documents;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.UUID;

@Document(indexName = "announcement")
@Data
public class ElasticAnnouncement {


    @Id
    private String aeid = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer)
    private Integer aid;

    @Field(type = FieldType.Text)
    private String atitle;

    @Field(type = FieldType.Text)
    private String adetail;

    @Field(type = FieldType.Boolean)
    private Boolean astatus;

    @Field(type = FieldType.Date_Range, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date adate;




}
