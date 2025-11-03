--liquibase formatted sql
--changeset Bushuev:1

ALTER TABLE albums ADD COLUMN name VARCHAR(32) NOT NULL UNIQUE;