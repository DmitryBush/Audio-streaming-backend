package com.bush.search.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "metadata")
public class Genre {
    @Id
    private Short genreId;
    @Field(type = FieldType.Text)
    private String name;
}
