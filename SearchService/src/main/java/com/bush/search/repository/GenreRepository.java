package com.bush.search.repository;

import com.bush.search.domain.document.Genre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GenreRepository extends ElasticsearchRepository<Genre, Short> {
}
