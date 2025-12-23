package com.bush.playlist.service.mapper;

import com.bush.playlist.dto.PlaylistTrackDto;
import com.bush.playlist.entity.PlaylistTracks;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaylistTrackReadMapper {
    PlaylistTrackDto mapToPlaylistTrackDto(PlaylistTracks playlistTracks);
}
