package com.bush.playlist.service.mapper;

import com.bush.playlist.dto.PlaylistReadDto;
import com.bush.playlist.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaylistReadMapper {
    @Mapping(target = "id", source = "playlistId")
    PlaylistReadDto mapToPlaylistReadDto(Playlist playlist);
}
