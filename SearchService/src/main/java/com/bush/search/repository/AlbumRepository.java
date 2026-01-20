package com.bush.search.repository;

import com.bush.search.domain.document.Album;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AlbumRepository extends ElasticsearchRepository<Album, Long> {
}
