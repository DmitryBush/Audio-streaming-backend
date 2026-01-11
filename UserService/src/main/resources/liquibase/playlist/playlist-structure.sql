CREATE TABLE playlists (
    playlist_id BIGSERIAL NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    creator_id VARCHAR(32) NOT NULL
);

CREATE TABLE playlist_tracks (
    playlist_id BIGINT NOT NULL,
    track_id BIGINT NOT NULL,

    PRIMARY KEY (playlist_id, track_id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(playlist_id) ON DELETE CASCADE
);