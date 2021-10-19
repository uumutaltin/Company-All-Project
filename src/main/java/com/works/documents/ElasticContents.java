package com.works.documents;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.UUID;

@Document(indexName = "contents")
@Data
public class ElasticContents {

    @Id
    private String ceid = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer)
    private Integer cid;

    @Field(type = FieldType.Text)
    private String ctitle;

    @Field(type = FieldType.Text)
    private String cdescription;

    @Field(type = FieldType.Text)
    private String cdetail;

    @Field(type = FieldType.Boolean)
    private Boolean cstatus;

    @Field(type = FieldType.Date_Range, format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    private Date cdate;


}
