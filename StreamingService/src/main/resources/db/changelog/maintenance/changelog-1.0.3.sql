--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE songs ALTER COLUMN name TYPE VARCHAR(128);

ALTER TABLE albums ALTER COLUMN name TYPE VARCHAR(128);

ALTER TABLE artists ALTER COLUMN name TYPE VARCHAR(128);