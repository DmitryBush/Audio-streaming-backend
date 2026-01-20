package com.bush.search.repository;

import com.bush.search.domain.document.Playlist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PlaylistRepository extends ElasticsearchRepository<Playlist, Long> {
}
