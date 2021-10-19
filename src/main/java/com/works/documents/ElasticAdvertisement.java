package com.works.documents;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

@Document(indexName = "advertisement")
@Data
public class ElasticAdvertisement {

    @Id
    private String aeid = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer)
    private Integer aid;


    @Field(type = FieldType.Text)
    private String atitle;


    @Field(type = FieldType.Integer)
    private Integer adesc;


    @Field(type = FieldType.Date_Range, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date datestart ;


    @Field(type = FieldType.Date_Range, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date dateend;


    @Field(type = FieldType.Text)
    private String advertimage;


    @Field(type = FieldType.Text)
    private String advertwidth;


    @Field(type = FieldType.Text)
    private String advertheight;


    @Field(type = FieldType.Text)
    private String advertlink;


    @Field(type = FieldType.Boolean)
    private Boolean astatus;

}