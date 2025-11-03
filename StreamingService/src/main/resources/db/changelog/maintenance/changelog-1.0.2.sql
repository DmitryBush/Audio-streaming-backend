--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE albums ADD COLUMN disc_count SMALLINT;

--changeset Bushuev:2

ALTER TABLE songs ADD COLUMN disc_number SMALLINT,
    ADD CONSTRAINT unique_track_per_disc UNIQUE(f_key_album_id, track_number_album, disc_number);