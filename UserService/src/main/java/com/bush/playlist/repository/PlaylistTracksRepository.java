package com.bush.playlist.repository;

import com.bush.playlist.entity.PlaylistTracks;
import com.bush.playlist.entity.PlaylistTracksId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTracksRepository extends JpaRepository<PlaylistTracks, PlaylistTracksId> {
    Page<PlaylistTracks> findByPlaylistPlaylistId(Long playlistId, Pageable pageable);
}
