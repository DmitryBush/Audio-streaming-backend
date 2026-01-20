package com.bush.search.repository;

import com.bush.search.domain.document.Artist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArtistRepository extends ElasticsearchRepository<Artist, Long> {
}
