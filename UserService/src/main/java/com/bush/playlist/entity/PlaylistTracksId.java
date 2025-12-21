package com.bush.playlist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@IdClass(PlaylistTracksId.class)
public class PlaylistTracksId {
    private Long playlist;
    private Long trackId;
//    @ManyToOne
//    @JoinColumn(name = "playlist_id", nullable = false)
//    private Playlist playlist;
//    @Column(name = "track_id", nullable = false)
//    private Long trackId;
}
