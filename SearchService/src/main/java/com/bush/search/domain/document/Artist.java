package com.bush.search.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "metadata")
public class Artist {
    @Id
    private Long artistId;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String biography;
}
