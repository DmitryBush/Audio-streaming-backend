package com.bush.playlist.repository;

import com.bush.playlist.entity.Playlist;
import com.bush.playlist.entity.PlaylistTracks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    
    Page<Playlist> findByUserId(String userId, Pageable pageable);

    Page<PlaylistTracks> findTracksByPlaylistId(Long playlistId, Pageable pageable);
}