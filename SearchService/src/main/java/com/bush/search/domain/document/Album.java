package com.bush.search.domain.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "metadata")
public class Album {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long albumId;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Date)
    private LocalDate releaseDate;
    @Field(type = FieldType.Short)
    private Short discCount;
    @Field(type = FieldType.Keyword)
    private String coverArtUrl;
    @Field(type = FieldType.Nested)
    private Artist artist;
    @Field(type = FieldType.Nested)
    private Genre genre;
}
