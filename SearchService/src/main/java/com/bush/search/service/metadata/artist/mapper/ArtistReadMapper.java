package com.bush.search.service.metadata.artist.mapper;

import com.bush.search.domain.document.Artist;
import com.bush.search.domain.dto.metadata.ArtistSearchResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistReadMapper {
    ArtistSearchResultDto mapToArtistSearchResultDto(Artist artist);
}
