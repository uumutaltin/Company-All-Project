package com.works.layers;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(value = "Advertisement", description = "Reklam veri ekleme için kullanılır.")
public class AdvertisementInterLayer {

    @NotNull(message = "Başlık null olamaz!")
    @NotEmpty(message = "Başlık boş olamaz!")
    @ApiModelProperty(value = "Reklam Başlığı ")
    private String atitle;

    @NotNull(message = "Gösterim sayısı null olamaz!")
    @ApiModelProperty(value = "Gösterim Sayısı ")
    private Integer adesc;

    @NotNull(message = "Yükseklik null olamaz!")
    @NotEmpty(message = "Yükseklik boş olamaz!")
    @ApiModelProperty(value = "Yükseklik ")
    private String advertheight;

    @NotNull(message = "Genişlik null olamaz!")
    @NotEmpty(message = "Genişlik boş olamaz!")
    @ApiModelProperty(value = "Genişlik ")
    private String advertwidth;
    @NotNull(message = "Tarih  null olamaz!")
    @NotEmpty(message = "Tarih  boş olamaz!")
    @ApiModelProperty(value = "Başlangıç Tarihi ")
    private String datestart;
    @NotNull(message = "Tarih null olamaz!")
    @NotEmpty(message = "Tarih  boş olamaz!")
    @ApiModelProperty(value = "Bitiş Tarihi ")
    private String dateend;
    @NotNull(message = "Link null olamaz!")
    @NotEmpty(message = "Link boş olamaz!")
    @ApiModelProperty(value = "Reklam linki ")
    private String advertlink;
}
