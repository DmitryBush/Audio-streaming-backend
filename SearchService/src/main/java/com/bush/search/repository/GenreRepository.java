package com.bush.search.repository;

import com.bush.search.domain.document.Genre;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface GenreRepository extends ElasticsearchRepository<Genre, Short> {
    List<Genre> findByNameContainingIgnoreCase(String name);
}
