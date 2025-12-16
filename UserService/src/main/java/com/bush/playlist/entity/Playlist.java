package com.bush.playlist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "playlists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Long playlistId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;

    @OneToMany(mappedBy = "id.playlist")
    private List<PlaylistTracks> tracks = new ArrayList<>();
}