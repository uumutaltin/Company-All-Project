package com.works.documents;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Document(indexName = "customer")
@Data
public class ElasticCustomer {

    @Id
    private String ceid = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer)
    private Integer cid;

    @Field(type = FieldType.Text)
    private String cname;

    @Field(type = FieldType.Text)
    private String csurname;

    @Field(type = FieldType.Text)
    private String  email;

    @Field(type = FieldType.Text)
    private String mobile_phone;

    @Field(type = FieldType.Boolean)
    private Boolean ban=false;

}
