package com.bush.search.domain.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "metadata")
public class Artist {
    @Id
    private Long artistId;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String biography;
}
