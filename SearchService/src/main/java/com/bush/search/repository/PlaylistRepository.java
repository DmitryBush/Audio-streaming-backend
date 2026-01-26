package com.bush.search.repository;

import com.bush.search.domain.document.Playlist;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PlaylistRepository extends ElasticsearchRepository<Playlist, Long> {
    List<Playlist> findByNameContainingIgnoreCase(String name);
}
