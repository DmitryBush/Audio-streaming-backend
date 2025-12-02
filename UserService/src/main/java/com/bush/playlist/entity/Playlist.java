package com.bush.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "playlists")
public class Playlist {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlist_id")
    private Integer playlistId;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "creator_id", nullable = false)
    private Integer creatorId;

    // Constructors
    public Playlist() {}
    
    public Playlist(String name, Integer creatorId) {
        this.name = name;
        this.creatorId = creatorId;
    }
    
    // Getters and Setters
    public Integer getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(Integer playlistId) {
        this.playlistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
}