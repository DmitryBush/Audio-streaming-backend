package com.bush.search.service.metadata.album.mapper;

import com.bush.search.domain.document.Album;
import com.bush.search.domain.index.AlbumPayload;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumCreateMapper {
    Album mapToAlbum(AlbumPayload albumPayload);
}
