package com.bush.search.repository;

import com.bush.search.domain.document.Artist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArtistRepository extends ElasticsearchRepository<Artist, Long> {
    List<Artist> findByNameContainingIgnoreCase(String name);
}
