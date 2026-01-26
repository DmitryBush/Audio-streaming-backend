package com.bush.search.service.metadata.album.mapper;

import com.bush.search.domain.document.Album;
import com.bush.search.domain.dto.metadata.AlbumSearchResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AlbumReadMapper {
    AlbumSearchResultDto mapToAlbumSearchResultDto(Album album);
}
