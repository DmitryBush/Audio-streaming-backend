package com.bush.search.domain.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "metadata")
public class Song {
    @Id
    private Long songId;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Short)
    private Short trackNumberAlbum;
    @Field(type = FieldType.Integer)
    private Integer duration;
    @Field(type = FieldType.Short)
    private Short discNumber;
    @Field(type = FieldType.Nested)
    private Artist artist;
    @Field(type = FieldType.Nested)
    private Album album;
}
