package com.bush.search.service.playlist.mapper;

import com.bush.search.domain.document.Playlist;
import com.bush.search.domain.index.PlaylistPayload;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaylistCreateMapper {
    Playlist mapToPlaylist(PlaylistPayload playlistPayload);
}
