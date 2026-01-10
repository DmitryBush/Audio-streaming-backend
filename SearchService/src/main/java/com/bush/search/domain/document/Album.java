package com.bush.search.domain.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@Document(indexName = "metadata")
public class Album {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Date)
    private LocalDate releaseDate;
    @Field(type = FieldType.Short)
    private Short discCount;
    @Field(type = FieldType.Nested)
    private Artist artist;
    @Field(type = FieldType.Nested)
    private Genre genre;
}
