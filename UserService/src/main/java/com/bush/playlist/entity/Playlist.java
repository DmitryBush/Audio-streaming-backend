package com.bush.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "playlists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Integer playlistId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;
}