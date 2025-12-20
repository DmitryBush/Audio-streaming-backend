--liquibase formatted sql
--changeset Bushuev:1

CREATE TABLE artists(
    artist_id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(32) NOT NULL UNIQUE,
    biography VARCHAR(255)
);

CREATE TABLE genres(
    genre_id SMALLINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(25) NOT NULL UNIQUE
);

CREATE TABLE albums(
    album_id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    release_date DATE,
    cover_art_url VARCHAR(255),
    f_key_artist_id BIGINT NOT NULL REFERENCES artists(artist_id),
    f_key_genre_id SMALLINT REFERENCES genres(genre_id)
);

CREATE TABLE songs(
    song_id BIGINT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(32) NOT NULL UNIQUE,
    track_number_album SMALLINT,
    duration INTEGER NOT NULL,
    file_url VARCHAR(255) NOT NULL,
    f_key_artist_id BIGINT REFERENCES artists(artist_id),
    f_key_album_id BIGINT REFERENCES albums(album_id)
);