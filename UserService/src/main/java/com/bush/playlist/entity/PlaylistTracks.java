package com.bush.playlist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "playlist_tracks")
@IdClass(PlaylistTracksId.class)
public class PlaylistTracks {
//    @EmbeddedId
//    private PlaylistTracksId id;
    @Id
    @ManyToOne
    @JoinColumn(name = "playlist_id", nullable = false)
    private Playlist playlist;
    @Id
    @Column(name = "track_id", nullable = false)
    private Long trackId;
}
