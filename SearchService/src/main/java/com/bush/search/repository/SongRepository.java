package com.bush.search.repository;

import com.bush.search.domain.document.Song;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SongRepository extends ElasticsearchRepository<Song, Long> {
}
