package com.works.documents;

import com.works.entities.ProductCategory;
import com.works.entities.ProductImages;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Document(indexName = "product")
@Data
public class ElasticProduct {
    @Id
    private String peid = UUID.randomUUID().toString();

    @Field(type = FieldType.Integer)
    private Integer pid;

    @Field(type = FieldType.Text)
    private  String pname;

    @Field(type = FieldType.Text)
    private  String psdetail;

    @Field(type = FieldType.Text)
    private  String pdetail;

    @Field(type = FieldType.Integer)
    private Integer pprice;

    @Field(type = FieldType.Integer)
    private Integer ptype;

    @Field(type = FieldType.Integer)
    private Integer pcampaign;

    @Field(type = FieldType.Text)
    private String pcampaignname;

    @Field(type = FieldType.Text)
    private String pcampaigndetail;

    @Field(type = FieldType.Text)
    private String padress;

    @Field(type = FieldType.Text)
    private String platitude ;

    @Field(type = FieldType.Text)
    private String plongitude;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<ProductImages> pimages;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<ProductCategory> pcategories;
}
