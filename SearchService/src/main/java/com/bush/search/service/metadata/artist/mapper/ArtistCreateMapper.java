package com.bush.search.service.metadata.artist.mapper;

import com.bush.search.domain.document.Artist;
import com.bush.search.domain.index.ArtistPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistCreateMapper {
    @Mapping(target = "artistId", source = "id")
    Artist mapToArtist(ArtistPayload artistPayload);
}
