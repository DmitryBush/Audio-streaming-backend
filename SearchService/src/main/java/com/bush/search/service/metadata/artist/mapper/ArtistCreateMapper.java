package com.bush.search.service.metadata.artist.mapper;

import com.bush.search.domain.document.Artist;
import com.bush.search.domain.index.ArtistPayload;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArtistCreateMapper {
    Artist mapToArtist(ArtistPayload artistPayload);
}
