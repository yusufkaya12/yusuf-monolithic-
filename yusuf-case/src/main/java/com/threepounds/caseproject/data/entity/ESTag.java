package com.threepounds.caseproject.data.entity;

import co.elastic.clients.util.DateTime;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Document(indexName = "tag")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ESTag{
    @Id
    private String id;

    @Field(name = "tag",type = FieldType.Text)
    private String tag;

    @Field(name = "advert",type = FieldType.Nested)
    private Advert advert;


}
