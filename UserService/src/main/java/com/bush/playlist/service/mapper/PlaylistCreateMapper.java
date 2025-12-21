package com.bush.playlist.service.mapper;

import com.bush.playlist.dto.PlaylistCreateDto;
import com.bush.playlist.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaylistCreateMapper {
    @Mapping(target = "tracks", expression = "java(java.util.Collections.emptySet())")
    @Mapping(target = "playlistId", ignore = true)
    @Mapping(target = "creatorId", ignore = true)
    Playlist mapToPlaylist(PlaylistCreateDto createDto);
}
