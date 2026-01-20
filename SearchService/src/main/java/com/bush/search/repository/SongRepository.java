package com.bush.search.repository;

import com.bush.search.domain.document.Song;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface SongRepository extends ElasticsearchRepository<Song, String> {
    List<Song> findByNameContainingIgnoreCase(String name);
}
