package com.bush.search.service.playlist.mapper;

import com.bush.search.domain.document.Playlist;
import com.bush.search.domain.index.PlaylistPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaylistCreateMapper {
    @Mapping(target = "id", source = "playlistId")
    Playlist mapToPlaylist(PlaylistPayload playlistPayload);
}
