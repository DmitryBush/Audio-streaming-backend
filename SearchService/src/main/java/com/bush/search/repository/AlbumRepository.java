package com.bush.search.repository;

import com.bush.search.domain.document.Album;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface AlbumRepository extends ElasticsearchRepository<Album, Long> {
    List<Album> findByNameContainingIgnoreCase(String name);
}
