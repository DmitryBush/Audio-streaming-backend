package com.bush.search.service.metadata.album.mapper;

import com.bush.search.domain.document.Album;
import com.bush.search.domain.index.AlbumPayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumCreateMapper {
    @Mapping(target = "albumId", source = "id")
    @Mapping(target = "artist", source = "artistPayload")
    @Mapping(target = "artist.artistId", source = "artistPayload.id")
    @Mapping(target = "genre", source = "genrePayload")
    @Mapping(target = "genre.genreId", source = "genrePayload.id")
    Album mapToAlbum(AlbumPayload albumPayload);
}
